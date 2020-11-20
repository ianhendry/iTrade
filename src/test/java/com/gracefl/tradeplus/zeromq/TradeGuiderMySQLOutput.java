package com.gracefl.tradeplus.zeromq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;
//import com.gracefl.tradeplus.repository.SignalServiceRepository;
//import com.gracefl.tradeplus.service.dto.SignalServiceDTO;
//import com.gracefl.tradeplus.service.impl.SignalServiceServiceImpl;
//import com.gracefl.tradeplus.service.mapper.SignalServiceMapper;

public class TradeGuiderMySQLOutput {

	private static Random rand = new Random(System.nanoTime());

	private enum ForexPair {EURO, POUND, GOLD}
	
	final static Integer EURUSD_VOL_LIMIT = 1050;
	final static Integer GBPUSD_VOL_LIMIT = 1200;
	final static Integer GOLD_VOL_LIMIT = 3500;
	
	final static Integer EURUSD_VSA_SEQUENCE_LIMIT = 20;
	final static Integer GBPUSD_VSA_SEQUENCE_LIMIT = 20;
	final static Integer GOLD_VSA_SEQUENCE_LIMIT = 20;
	
	static int eurBarCount = 0;
	static int gbpBarCount = 0;
	static int goldBarCount = 0;
	static int eurBarsSinceHighVol = 0;
	static int gbpBarsSinceHighVol = 0;
	static int goldBarsSinceHighVol = 0;
    
	static List<ForexPair> highVolBarPresent = new ArrayList<>();
	
	private static List<InstrumentOHLC> eurUsdBarHistory = new ArrayList<>();
	private static List<InstrumentOHLC> gbpUsdBarHistory = new ArrayList<>();  
	private static List<InstrumentOHLC> goldBarHistory = new ArrayList<>();  
	
	public static void main(String[] args) throws JsonMappingException, java.io.IOException, SQLException {

		Statement stmt = null;
		      
		String priceDataHistoryQuery = "INSERT INTO price_data_history (price_timeframe,price_date,price_time,price_open,price_high,price_low,price_close,price_volume,instrument_id) " +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        	
		String signalsServiceQuery = "INSERT INTO signal_service (alert_date,alert_time,alert_text,alert_description,signal_indicates,image,image_content_type,timeframe,alert_price,is_sequence_present,bar_volume,bar_size,bar_close,is_published,trade_signals_id,price_data_history_id) " +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection con1 = DriverManager.getConnection ( 
		         "jdbc:mysql://localhost:3306/itrade","root","plockton");
		      PreparedStatement updatePriceDataHistory = con1.prepareStatement(priceDataHistoryQuery, Statement.RETURN_GENERATED_KEYS);
		      
		Connection con2 = DriverManager.getConnection ( 
		         "jdbc:mysql://localhost:3306/itrade","root","plockton");
		      PreparedStatement updateSignalService = con2.prepareStatement(signalsServiceQuery);
				      
		Connection con3 = DriverManager.getConnection ( 
		         "jdbc:mysql://localhost:3306/itrade","root","plockton");
        stmt = con3.createStatement();
				      
		String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
		ZContext ctx = new ZContext();
        ZMQ.Socket subscriber = ctx.createSocket(SocketType.SUB);
        subscriber.setIdentity(identity.getBytes(ZMQ.CHARSET));
        
        // Synchronize with the publisher
		// Create context and socket for PUSH
        ZMQ.Socket pushSock = ctx.createSocket(SocketType.PUSH);
        
        subscriber.subscribe("".getBytes());
        subscriber.connect("tcp://localhost:32770");
        pushSock.connect("tcp://localhost:32768");
        pushSock.send("TRACK_RATES;EURUSD;5;GBPUSD;5;GOLD;5".getBytes(), 0);

        //#DJ30_Z20 - and add the 1hr bars
        
        try {
            String msg = "";
            String signalText[];
            
            while (!msg.equalsIgnoreCase("END")) {
            	
            	// get JSON message and convert to POJO
                msg = new String(subscriber.recv(0));
                Integer id = 0;
                int count=0;
                
                ObjectMapper objectMapper = new ObjectMapper();
                InstrumentOHLC newBar = objectMapper.readValue(msg, InstrumentOHLC.class);

                String sql = "SELECT id FROM instrument WHERE ticker = '" + newBar.getInstrument() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                   //Retrieve by column name
                	count++;
                   id  = rs.getInt("id");
                }
                
                if (count>1) {
                	System.out.println("ERROR - more than 1 Instrument returned on lookup");
                }
                rs.close();
                
                // print the data to terminal
                System.out.println(java.time.LocalTime.now() + " " + newBar.getInstrument() + " Volume: " + newBar.getTickVolume());
                updatePriceDataHistory.setString(1, getTimeFrameFromPeriod(newBar.getPeriod()).toString());
                updatePriceDataHistory.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                //updatePriceDataHistory.setDate(2, java.sql.Date.valueOf(ZonedDateTime.now()));
                
                updatePriceDataHistory.setDate(3, getCurrentDatetime());
                updatePriceDataHistory.setDouble(4,newBar.getOpenPrice());
                updatePriceDataHistory.setDouble(5,newBar.getHighPrice());
                updatePriceDataHistory.setDouble(6,newBar.getLowPrice());
                updatePriceDataHistory.setDouble(7,newBar.getClosePrice());
                updatePriceDataHistory.setDouble(8,newBar.getTickVolume());
                updatePriceDataHistory.setInt(9, id); 
                
                Integer newId = updatePriceDataHistory.executeUpdate();
                
                VSABackgroundScanner();
                
                signalText = VSAScanner(newBar);
                if (!signalText[0].equalsIgnoreCase("NO_SIGNAL")) {
                	
                	// update the signals DB
                	updateSignalService.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                	updateSignalService.setDate(2, getCurrentDatetime());
                	updateSignalService.setString(3, signalText[2]);
                	updateSignalService.setString(4, signalText[3]);
                	updateSignalService.setString(5, signalText[4]);
                	updateSignalService.setString(6, null); // image
                	updateSignalService.setString(7, null); // image type
                	updateSignalService.setString(8, getTimeFrameFromPeriod(newBar.getPeriod()).toString());
                	updateSignalService.setDouble(9, newBar.getClosePrice()); // alert price
                	updateSignalService.setBoolean(10, false); // isSequence
                	updateSignalService.setDouble(11, newBar.getTickVolume()); // bar volume
                	updateSignalService.setString(12, "HUGE"); // bar size
                	updateSignalService.setString(13, "STRONG"); // bar close
                	updateSignalService.setBoolean(14, false); // is published
                	//updateSignalService.setDouble(15, 0); // trade_signals_id
                	updateSignalService.setDouble(16, newId); // price_data_history_id - the ID of the bar just saved above
                	
                	updateSignalService.executeUpdate();
                	
                }
                
                /*
                 * SMALL, MEDIUM, LARGE, VERYLARGE, GIGANTIC - bar size
                 * MIDDLE, TOP, LOW, MIDTOP, MIDLOW - bar close
                 */ 
                
            }
        }
        catch(org.zeromq.ZMQException ex){
           // break;
        } finally {
			pushSock.close();
		    subscriber.close();
		 	ctx.close();
		 	
		 	con1.close();
		 	con2.close();
		 	
        }
	}

	public static java.sql.Date getCurrentDatetime() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	private static void VSABackgroundScanner() {
		//TODO - background volume scanner
        // agregate the signs of strenght and signs of weakness over the last few days to determine the background - list show a list of the history
        // do this by saving each outout to the data base and then view in the UI
	}
	
	private static String[] VSAScanner(InstrumentOHLC newBar) throws IOException {
		
		ForexPair pair=null;
        //TODO check Point in MT4 for GOLD
        double point = 0.00001; // this is for currency pairs but not gold
        double averageSpread = 0;
        double averageVolume = 0;
        double spreadHL = 0;
        double middleOfBar = 0; 
		double upOfBar = 0;    
		double downOfBar = 0;
		InstrumentOHLC b1 = null;
		InstrumentOHLC b2 = null;
		InstrumentOHLC b3 = null;
		InstrumentOHLC b4 = null;
		
		String[] signalText = new String[4];
		
    	// figure out which pair came in
    	if (newBar.getInstrument().equals("EURUSD")) {
    		eurBarCount++;
    		pair = ForexPair.EURO;
    		eurUsdBarHistory.add(newBar);
    	} else if (newBar.getInstrument().equals("GBPUSD")) {
    		gbpBarCount++;
    		pair = ForexPair.POUND;
    		gbpUsdBarHistory.add(newBar);
    	} else if (newBar.getInstrument().equals("GOLD")) {
    		goldBarCount++;
    		pair = ForexPair.GOLD;
    		goldBarHistory.add(newBar);
    	}
	        	
    	if (pair == ForexPair.EURO) {
    		if (highVolBarPresent.contains(ForexPair.EURO)) {
    			eurBarsSinceHighVol++;
    		}
    		
    		if (newBar.getTickVolume() > EURUSD_VOL_LIMIT) {
    			highVolBarPresent.add(ForexPair.EURO);	             
            	java.awt.Toolkit.getDefaultToolkit().beep();
            	System.out.println("EURUSD Volume Alert");
            }
    		
    		if (eurUsdBarHistory.size() > 4) {
    			b1 = eurUsdBarHistory.get(eurUsdBarHistory.size()-1);
    			b2 = eurUsdBarHistory.get(eurUsdBarHistory.size()-2);
    			b3 = eurUsdBarHistory.get(eurUsdBarHistory.size()-3);
    			b4 = eurUsdBarHistory.get(eurUsdBarHistory.size()-4);

    			middleOfBar   = (b1.getHighPrice()+b1.getLowPrice())/2;                             // EXACT MIDDLE 
        		upOfBar       = (b1.getHighPrice()+b1.getLowPrice())/2 + (b1.getHighPrice()-b1.getLowPrice())/3.6;  // UP CLOSE    
        		downOfBar     = (b1.getHighPrice()+b1.getLowPrice())/2 - (b1.getHighPrice()-b1.getLowPrice())/3.6;  // DOWN CLOSE  
        		
    			for (int i = 0; i < eurUsdBarHistory.size(); i++) {
    				averageVolume = averageVolume + eurUsdBarHistory.get(i).getTickVolume();  
    				averageSpread = averageSpread + (b2.getHighPrice() - b2.getLowPrice())/point;
    			}
    	
    		    averageVolume = averageVolume/eurUsdBarHistory.size();
    		    spreadHL = (b2.getHighPrice() - b2.getLowPrice())/point;   
    		    averageSpread = averageSpread/eurUsdBarHistory.size();
    		}
    		
			if (highVolBarPresent.contains(ForexPair.EURO) && eurBarsSinceHighVol > EURUSD_VSA_SEQUENCE_LIMIT) {
            	// reset everything and wait for another high volume bar
				highVolBarPresent.remove(highVolBarPresent.indexOf(ForexPair.EURO));
            	eurBarsSinceHighVol = 0;
            	eurBarCount = 0;
            	System.out.println("EURUSD resetting HIGH volume after 20 bars");
            }	
    	}
    	
    	if (pair == ForexPair.POUND) {
    		if (highVolBarPresent.contains(ForexPair.POUND)) {
    			gbpBarsSinceHighVol++;
    		}
    		
    		if (newBar.getTickVolume() > GBPUSD_VOL_LIMIT) {
    			highVolBarPresent.add(ForexPair.POUND);	             
            	java.awt.Toolkit.getDefaultToolkit().beep();
            	System.out.println("GBPUSD Volume Alert");
            }
    		
    		if (gbpUsdBarHistory.size() > 4) {
    			b1 = gbpUsdBarHistory.get(gbpUsdBarHistory.size()-1);
    			b2 = gbpUsdBarHistory.get(gbpUsdBarHistory.size()-2);
    			b3 = gbpUsdBarHistory.get(gbpUsdBarHistory.size()-3);
    			b4 = gbpUsdBarHistory.get(gbpUsdBarHistory.size()-4);

    			middleOfBar   = (b1.getHighPrice()+b1.getLowPrice())/2;                             // EXACT MIDDLE 
        		upOfBar       = (b1.getHighPrice()+b1.getLowPrice())/2 + (b1.getHighPrice()-b1.getLowPrice())/3.6;  // UP CLOSE    
        		downOfBar     = (b1.getHighPrice()+b1.getLowPrice())/2 - (b1.getHighPrice()-b1.getLowPrice())/3.6;  // DOWN CLOSE  
        		
    			for (int i = 0; i < gbpUsdBarHistory.size(); i++) {
    				averageVolume = averageVolume + gbpUsdBarHistory.get(i).getTickVolume();  
    				averageSpread = averageSpread + (b2.getHighPrice() - b2.getLowPrice())/point;
    			}
    	
    		    averageVolume = averageVolume/gbpUsdBarHistory.size();
    		    spreadHL = (b2.getHighPrice() - b2.getLowPrice())/point;   
    		    averageSpread = averageSpread/gbpUsdBarHistory.size();
    		}
    		
			if (highVolBarPresent.contains(ForexPair.POUND) && gbpBarsSinceHighVol > GBPUSD_VSA_SEQUENCE_LIMIT) {
            	// reset everything and wait for another high volumme bar
				highVolBarPresent.remove(highVolBarPresent.indexOf(ForexPair.POUND));
            	gbpBarsSinceHighVol = 0;
            	gbpBarCount = 0;
            	System.out.println("GBPUSD resetting HIGH volume after 20 bars");
            }	
    	}
    	
    	if (pair == ForexPair.GOLD) {
    		if (highVolBarPresent.contains(ForexPair.GOLD)) {
    			goldBarsSinceHighVol++;
    		}
    		
    		if (newBar.getTickVolume() > GOLD_VOL_LIMIT) {
    			highVolBarPresent.add(ForexPair.GOLD);	             
            	java.awt.Toolkit.getDefaultToolkit().beep();
            	System.out.println("GOLD Volume Alert");
            }
    		
    		if (goldBarHistory.size() > 4) {
    			b1 = goldBarHistory.get(goldBarHistory.size()-1);
    			b2 = goldBarHistory.get(goldBarHistory.size()-2);
    			b3 = goldBarHistory.get(goldBarHistory.size()-3);
    			b4 = goldBarHistory.get(goldBarHistory.size()-4);

    			middleOfBar   = (b1.getHighPrice()+b1.getLowPrice())/2;                             // EXACT MIDDLE 
        		upOfBar       = (b1.getHighPrice()+b1.getLowPrice())/2 + (b1.getHighPrice()-b1.getLowPrice())/3.6;  // UP CLOSE    
        		downOfBar     = (b1.getHighPrice()+b1.getLowPrice())/2 - (b1.getHighPrice()-b1.getLowPrice())/3.6;  // DOWN CLOSE  
        		
    			for (int i = 0; i < goldBarHistory.size(); i++) {
    				averageVolume = averageVolume + goldBarHistory.get(i).getTickVolume();  
    				averageSpread = averageSpread + (b2.getHighPrice() - b2.getLowPrice())/point;
    			}
    	
    		    averageVolume = averageVolume/goldBarHistory.size();
    		    spreadHL = (b2.getHighPrice() - b2.getLowPrice())/point;   
    		    averageSpread = averageSpread/goldBarHistory.size();
    		}
    		
			if (highVolBarPresent.contains(ForexPair.GOLD) && goldBarsSinceHighVol > GOLD_VSA_SEQUENCE_LIMIT) {
            	// reset everything and wait for another high volumme bar
				highVolBarPresent.remove(highVolBarPresent.indexOf(ForexPair.GOLD));
            	goldBarsSinceHighVol = 0;
            	goldBarCount = 0;
            	System.out.println("GOLD resetting HIGH volume after 20 bars");
            }	
    	}
    	
    	// run scan when we've seeded 4 bars
    	if (b1 != null && b2 != null && b3 != null && b4 != null) {
    		signalText = runVolumeScan(b1, b2, b3, b4, middleOfBar, upOfBar, downOfBar, spreadHL, averageVolume, averageSpread, pair);
    	} else {
    		signalText[0] = "NO_SIGNAL";
    	}

    	//TODO
    	// run a clean up to e.g. only keep the last 30 bars in the history by removing the oldest bar from the ArrayList 
    	// write results to a CSV that can be loaded into MYSQL via dataload
    	// filter the ui results for a pair so can see the history as market develops per pair
    	// synchronize the history data with a CSV uploader or new MQ call to get data 
    	
		return signalText;
	}
	
	private static String[] runVolumeScan(InstrumentOHLC b1, InstrumentOHLC b2, InstrumentOHLC b3, InstrumentOHLC b4, double MIDDLEOFBAR, double UPOFBAR, double DOWNOFBAR, double spreadHL, double averageVolume, double averageSpread, ForexPair pair) {
		System.out.println(pair + " volume scan START");	
		String[] signal = new String[5];
		
		signal[0] = LocalDate.now().toString();
		signal[1] = LocalTime.now().toString();
		
		boolean hasSignal = false;

		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " REGULAR UPTHRUST / Weakness - SM marked up the prices indicating strong bullishness. Get ready for MarkDown. ");
			signal[2] = "REGULAR UPTHRUST";
			signal[3] = "SM marked up the prices indicating strong bullishness. Get ready for MarkDown";
			signal[4] = "WEAKNESS";
			hasSignal = true;
		}
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume*2) {
			System.out.println(pair + " HighVol UPTHRUST / Weakness - SM marked up the prices indicating strong bullishness. Get ready for MarkDown. ");
			signal[2] = "HighVol UPTHRUST";
			signal[3] = "SM marked up the prices indicating strong bullishness. Get ready for MarkDown";
			signal[4] = "WEAKNESS";
			hasSignal = true;
		}
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() > DOWNOFBAR  && b2.getClosePrice() < UPOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " Unsucessfull UPTHRUST / Demand stronger - SM was not successful in marking the price down. There was too much demand. ");  //
			signal[2] = "Unsucessfull UPTHRUST";
			signal[3] = "SM was not successful in marking the price down. There was too much demand";
			signal[4] = "STRENGTH";
			hasSignal = true;
		}
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL < averageSpread && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() < averageVolume) {
			System.out.println(pair + " PSEUDO UPTHRUST / Weakness - Upthrusts with low volume. Sign of weakness  ");  //
			signal[2] = "PSEUDO UPTHRUST";
			signal[3] = "Upthrusts with low volume. Sign of weakness";
			signal[4] = "WEAKNESS";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL < averageSpread*0.8 && b2.getTickVolume() < averageVolume) {
			System.out.println(pair + " NO DEMAND / Weakness - No support from the SM. The SM is not interested in higher prices.  "); //
			signal[2] = "NO DEMAND";
			signal[3] = "No support from the SM. The SM is not interested in higher prices";
			signal[4] = "WEAKNESS";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() > UPOFBAR && spreadHL > averageSpread*1.5 && b2.getTickVolume() > b3.getTickVolume()) {
			System.out.println(pair + " MARK UP / Weakness - Effort to move up. SM will test the market for supply before trying to move up further. ");  //
			signal[2] = "MARK UP";
			signal[3] = "Effort to move up. SM will test the market for supply before trying to move up further";
			signal[4] = "WEAKNESS";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL > averageSpread*1.5 && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " FAILED MARK UP / Strenght - Effort to move up. SM will test the market for supply before trying to move up further.  ");  //
			signal[2] = "FAILED MARK UP";
			signal[3] = "Effort to move up. SM will test the market for supply before trying to move up further";
			signal[4] = "STRENGTH";
			hasSignal = true;
		}
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() > UPOFBAR && b2.getTickVolume() < b3.getTickVolume() && b2.getTickVolume() < averageVolume) {
			System.out.println(pair + " TESTING FOR SUPPLY / Strenght - Marking down the price. Low volume or less trading activity indicates a successful test. ");  //
			signal[2] = "TESTING FOR SUPPLY";
			signal[3] = "Marking down the price. Low volume or less trading activity indicates a successful test";
			signal[4] = "STRENGTH";
			hasSignal = true;
		}
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() > MIDDLEOFBAR && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " STOPPING VOLUME / Strength - SM is absorbing the price. SM has decided to stop the down tide and start accumulating. ");  //
			signal[2] = "STOPPING VOLUME";
			signal[3] = "SM is absorbing the price. SM has decided to stop the down tide and start accumulating";
			signal[4] = "STRENGTH";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() > MIDDLEOFBAR && b2.getLowPrice() < b3.getLowPrice() && b3.getLowPrice() < b4.getLowPrice() && spreadHL > averageSpread*1.5 && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " REVERSE UPTHRUST / Strength - This is a good sign of strength returning and you find the trend reversing almost immediately. ");  //
			signal[2] = "REVERSE UPTHRUST";
			signal[3] = "This is a good sign of strength returning and you find the trend reversing almost immediately";
			signal[4] = "STRENGTH";
			hasSignal = true;
		}
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL < averageSpread*0.8 && b1.getTickVolume() < averageVolume) {
			System.out.println(pair + " NO SUPPLY / Strength - In up trend are indications of continued trend. Strength, especially if they appear before/after test bars. ");  //
			signal[2] = "NO SUPPLY";
			signal[3] = "In up trend are indications of continued trend. Strength, especially if they appear before/after test bars";
			signal[4] = "STRENGTH";
			hasSignal = true;
		}
			
		System.out.println(pair + " volume scan END");
		
		if (!hasSignal) {
			signal[0] = "NO_SIGNAL";
		} 
		
		return signal;
	}
	
	private static TIMEFRAME getTimeFrameFromPeriod(String period) {
		
		TIMEFRAME timeframe;
		
		switch(period)   {
			//case ("M1"): timeframe=TIMEFRAME.M1;
			case ("M5"): timeframe=TIMEFRAME.M5;
			case ("M15"): timeframe=TIMEFRAME.M15;
			case ("M30"): timeframe=TIMEFRAME.M30;
			case ("H1"): timeframe=TIMEFRAME.H1;
			case ("H4"): timeframe=TIMEFRAME.H4;
			case ("D1"): timeframe=TIMEFRAME.D1;
			case ("W1"): timeframe=TIMEFRAME.W1;
			case ("M1"): timeframe=TIMEFRAME.M1;
			default: timeframe=TIMEFRAME.M5;
		}
		
		return timeframe;
	}
	
}
