package com.gracefl.tradeplus.zeromq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

public class TradeGuiderCSVOutput {

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
	
	public static void main(String[] args) throws JsonMappingException, java.io.IOException {

		/*
		File priceFile = new File("price_history.csv");
		File serviceFile = new File("signal_service.csv");
		
		if (priceFile.createNewFile()) {
			System.out.println("New price_history.csv File is created!");
		} else {
			
		}
		*/
		FileWriter priceDataHistoryFile = new FileWriter("price_history.csv", true);
		//priceDataHistory.append("id");
		//priceDataHistory.append(",");
		priceDataHistoryFile.append("timeframe");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("date");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("time");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("open");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("high");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("low");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("close");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("volume");
		priceDataHistoryFile.append(",");
		priceDataHistoryFile.append("instrument");
		priceDataHistoryFile.append("\n");
		
		FileWriter signalServiceFile = new FileWriter("signal_service.csv", true);
		//signalService.append("id");
		//signalService.append(",");
		signalServiceFile.append("date");
		signalServiceFile.append(",");
		signalServiceFile.append("time");
		signalServiceFile.append(",");
		signalServiceFile.append("text");
		signalServiceFile.append(",");
		signalServiceFile.append("descriptions");
		signalServiceFile.append(",");
		signalServiceFile.append("indicates");
		signalServiceFile.append(",");
		signalServiceFile.append("image");
		signalServiceFile.append(",");
		signalServiceFile.append("image_type");
		signalServiceFile.append(",");
		signalServiceFile.append("alertprice");
		signalServiceFile.append(",");
		signalServiceFile.append("issequence");
		signalServiceFile.append(",");
		signalServiceFile.append("barVol");
		signalServiceFile.append(",");
		signalServiceFile.append("barSize");
		signalServiceFile.append(",");
		signalServiceFile.append("barClose");
		signalServiceFile.append(",");
		signalServiceFile.append("isPublished");
		signalServiceFile.append(",");
		signalServiceFile.append("linkToTradeSignal");
		signalServiceFile.append(",");
		signalServiceFile.append("linkToPriceDataHistory");
		signalServiceFile.append("\n");
		
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

        try {
            String msg = "";
            while (!msg.equalsIgnoreCase("END")) {
            	
            	// get JSON message and convert to POJO
                msg = new String(subscriber.recv(0));
                
                ObjectMapper objectMapper = new ObjectMapper();
                InstrumentOHLC newBar = objectMapper.readValue(msg, InstrumentOHLC.class);

                // print the data to terminal
                System.out.println(java.time.LocalTime.now() + " " + newBar.getInstrument() + " Volume: " + newBar.getTickVolume());
                priceDataHistoryFile.append(getTimeFrameFromPeriod(newBar.getPeriod()).toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(LocalDate.now().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(LocalTime.now().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(newBar.getOpenPrice().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(newBar.getHighPrice().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(newBar.getLowPrice().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(newBar.getClosePrice().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(newBar.getTickVolume().toString());
        		priceDataHistoryFile.append(",");
        		priceDataHistoryFile.append(newBar.getInstrument().toString());
        		priceDataHistoryFile.append("\n");
        		
        		priceDataHistoryFile.flush();
        		
                VSABackgroundScanner();
                
                VSAScanner(newBar, signalServiceFile);
                
            }
        }
        catch(org.zeromq.ZMQException ex){
           // break;
        } finally {
			pushSock.close();
		    subscriber.close();
		 	ctx.close();
		 	
		 	priceDataHistoryFile.flush();
		 	priceDataHistoryFile.close();
		 	signalServiceFile.flush();
		 	signalServiceFile.close();
        }
	}

	private static void VSABackgroundScanner() {
		//TODO - background volume scanner
        // agregate the signs of strenght and signs of weakness over the last few days to determine the background - list show a list of the history
        // do this by saving each outout to the data base and then view in the UI
	}
	
	private static void VSAScanner(InstrumentOHLC newBar, FileWriter signalServiceFile) throws IOException {
		
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
    		String signal = runVolumeScan(b1, b2, b3, b4, middleOfBar, upOfBar, downOfBar, spreadHL, averageVolume, averageSpread, pair);
    		if (signal != "NO_SIGNAL") {
    			signalServiceFile.append(signal);
    			signalServiceFile.append(",");
    			signalServiceFile.append(newBar.getClosePrice().toString()); //alertprice
    			signalServiceFile.append(",");
    			signalServiceFile.append(""); //issequence
    			signalServiceFile.append(",");
    			signalServiceFile.append(newBar.getTickVolume().toString());
    			signalServiceFile.append(",");
    			signalServiceFile.append(""); //barsize BARSIZE emun
    			signalServiceFile.append(",");
    			signalServiceFile.append(""); // barclose STRONG/WEAK etc
    			signalServiceFile.append(",");
    			signalServiceFile.append(""); // isPublished
    			signalServiceFile.append(",");
    			signalServiceFile.append(""); // linkToTradeSignal
    			signalServiceFile.append(",");
    			signalServiceFile.append(""); // linkToPriceDataHistory
    			signalServiceFile.append("\n");
    			
    			signalServiceFile.flush();
        		
    		}
    	}

    	//TODO
    	// run a clean up to e.g. only keep the last 30 bars in the history by removing the oldest bar from the ArrayList 
    	// write results to a CSV that can be loaded into MYSQL via dataload
    	// filter the ui results for a pair so can see the history as market develops per pair
		
	}
	
	private static String runVolumeScan(InstrumentOHLC b1, InstrumentOHLC b2, InstrumentOHLC b3, InstrumentOHLC b4, double MIDDLEOFBAR, double UPOFBAR, double DOWNOFBAR, double spreadHL, double averageVolume, double averageSpread, ForexPair pair) {
		System.out.println(pair + " volume scan START");	
		String signal= LocalDate.now().toString() + "," + LocalTime.now().toString();
		boolean hasSignal = false;

		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " REGULAR UPTHRUST / Weakness - SM marked up the prices indicating strong bullishness. Get ready for MarkDown. ");
			signal = "REGULAR UPTHRUST,SM marked up the prices indicating strong bullishness. Get ready for MarkDown,WEAKNESS,,";
			hasSignal = true;
		}
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume*2) {
			System.out.println(pair + " HighVol UPTHRUST / Weakness - SM marked up the prices indicating strong bullishness. Get ready for MarkDown. ");
			signal = "HighVol UPTHRUST,SM marked up the prices indicating strong bullishness. Get ready for MarkDown,WEAKNESS,,";
			hasSignal = true;
		}
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() > DOWNOFBAR  && b2.getClosePrice() < UPOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " Unsucessfull UPTHRUST / Demand stronger - SM was not successful in marking the price down. There was too much demand. ");  //
			signal = " Unsucessfull UPTHRUST,SM was not successful in marking the price down. There was too much demand,STRENGTH,,";
			hasSignal = true;
		}
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL < averageSpread && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() < averageVolume) {
			System.out.println(pair + " PSEUDO UPTHRUST / Weakness - Upthrusts with low volume. Sign of weakness  ");  //
			signal = "PSEUDO UPTHRUST,Upthrusts with low volume. Sign of weakness,WEAKNESS,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL < averageSpread*0.8 && b2.getTickVolume() < averageVolume) {
			System.out.println(pair + " NO DEMAND / Weakness - No support from the SM. The SM is not interested in higher prices.  "); //
			signal = "NO DEMAND,No support from the SM. The SM is not interested in higher prices,WEAKNESS,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() > UPOFBAR && spreadHL > averageSpread*1.5 && b2.getTickVolume() > b3.getTickVolume()) {
			System.out.println(pair + " MARK UP / Weakness - Effort to move up. SM will test the market for supply before trying to move up further. ");  //
			signal = "MARK UP,Effort to move up. SM will test the market for supply before trying to move up further,WEAKNESS,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL > averageSpread*1.5 && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " FAILED MARK UP / Strenght - Effort to move up. SM will test the market for supply before trying to move up further.  ");  //
			signal = "FAILED MARK UP,Effort to move up. SM will test the market for supply before trying to move up further,STRENGTH,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() > UPOFBAR && b2.getTickVolume() < b3.getTickVolume() && b2.getTickVolume() < averageVolume) {
			System.out.println(pair + " TESTING FOR SUPPLY / Strenght - Marking down the price. Low volume or less trading activity indicates a successful test. ");  //
			signal = "TESTING FOR SUPPLY,Marking down the price. Low volume or less trading activity indicates a successful test,STRENGTH,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() > MIDDLEOFBAR && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " STOPPING VOLUME / Strength - SM is absorbing the price. SM has decided to stop the down tide and start accumulating. ");  //
			signal = "STOPPING VOLUME,SM is absorbing the price. SM has decided to stop the down tide and start accumulating,STRENGTH,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() > MIDDLEOFBAR && b2.getLowPrice() < b3.getLowPrice() && b3.getLowPrice() < b4.getLowPrice() && spreadHL > averageSpread*1.5 && b2.getTickVolume() > averageVolume) {
			System.out.println(pair + " REVERSE UPTHRUST / Strength - This is a good sign of strength returning and you find the trend reversing almost immediately. ");  //
			signal = "REVERSE UPTHRUST,This is a good sign of strength returning and you find the trend reversing almost immediately,STRENGTH,,";
			hasSignal = true;
		}
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL < averageSpread*0.8 && b1.getTickVolume() < averageVolume) {
			System.out.println(pair + " NO SUPPLY / Strength - In up trend are indications of continued trend. Strength, especially if they appear before/after test bars. ");  //
			signal = "NO SUPPLY,In up trend are indications of continued trend. Strength, especially if they appear before/after test bars,STRENGTH,,";
			hasSignal = true;
		}
			
		System.out.println(pair + " volume scan END");
		
		if (hasSignal) {
			return signal;
		} else {
			return "NO_SIGNAL";
		}
		
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
