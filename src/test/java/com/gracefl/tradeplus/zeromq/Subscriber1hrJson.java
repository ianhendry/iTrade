package com.gracefl.tradeplus.zeromq;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;

public class Subscriber1hrJson {

	private static Random rand = new Random(System.nanoTime());
	/*
	final static Integer EURUSD_VOL_LIMIT = 1050;
	final static Integer GBPUSD_VOL_LIMIT = 1200;
	final static Integer GOLD_VOL_LIMIT = 3500;
	*/
	final static Integer EURUSD_VOL_LIMIT = 1050;
	final static Integer GBPUSD_VOL_LIMIT = 1200;
	final static Integer GOLD_VOL_LIMIT = 3500;
	
	static boolean eurUsdHighVolBarPresent = false;
	static boolean gbpUsdHighVolBarPresent = false;
	static boolean goldHighVolBarPresent = false;
	
	static List<InstrumentOHLC> eurUsdBarHistory = new ArrayList<>();
	static List<InstrumentOHLC> gbpUsdBarHistory = new ArrayList<>();  
	static List<InstrumentOHLC> goldBarHistory = new ArrayList<>();  

	
	public static void main(String[] args) throws JsonMappingException, java.io.IOException {

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
        //pushSock.send("TRACK_RATES;EURUSD;5;GBPUSD;5;GOLD;5;EURUSD;60;EURUSD;1440".getBytes(), 0);
        pushSock.send("TRACK_RATES;EURUSD;60;GBPUSD;60;GOLD;60;".getBytes(), 0);

        try {
            String msg = "";
            while (!msg.equalsIgnoreCase("END")) {
            	
            	// get JSON message and convert to POJO
                msg = new String(subscriber.recv(0));
                
                ObjectMapper objectMapper = new ObjectMapper();
                InstrumentOHLC newBar = objectMapper.readValue(msg, InstrumentOHLC.class);

                // print the data to terminal
                System.out.println(java.time.LocalTime.now() + " " + newBar.getInstrument() + " time frame=" + newBar.getPeriod() + " Volume: " + newBar.getTickVolume());
                
                if (newBar.getPeriod().equals("M5")) {
                	fiveMinuteVolumeScan(newBar);
                }
                
            }
        }
        catch(org.zeromq.ZMQException ex){
           // break;
        } finally {
			pushSock.close();
		    subscriber.close();
		 	ctx.close();
        }
	}
	
	private static void printDebug(String instrument, InstrumentOHLC latestBar, InstrumentOHLC checkBar1, InstrumentOHLC checkBar2) {
		System.out.println(instrument + " volume check newbar: " + latestBar.getTickVolume());
		System.out.println(instrument + " volume check bar1: " + checkBar1.getTickVolume());
		System.out.println(instrument + " volume check bar2: " + checkBar2.getTickVolume());
	}
	
	private static void fiveMinuteVolumeScan(InstrumentOHLC newBar) {
		
		int eurBarCount = 0;
        int gbpBarCount = 0;
        int goldBarCount = 0;
        int eurBarsSinceHighVol = 0;
        int gbpBarsSinceHighVol = 0;
        int goldBarsSinceHighVol = 0;
        
		// process for EURUSD 
        if (newBar.getInstrument().equals("EURUSD")) {
        	eurUsdBarHistory.add(newBar);
        	eurBarCount++;
        	//System.out.println("EURUSD bar count " + eurBarCount);
        	
        	if (eurUsdHighVolBarPresent) {
        		eurBarsSinceHighVol++;
        	}
        	
            if (newBar.getTickVolume() > EURUSD_VOL_LIMIT) {
            	eurUsdHighVolBarPresent = true;	             
            	java.awt.Toolkit.getDefaultToolkit().beep();
            	System.out.println("EURUSD Volume Alert");
            }
            if (eurUsdHighVolBarPresent && eurBarsSinceHighVol > 2) {
            	// check for no demand/supply in previous 2 bars
            	// NOTE - the current just closed bar was already added to the history
            	// hence we need size -2 & size -3 for the previous 2 bars
            	InstrumentOHLC checkBar1 = eurUsdBarHistory.get(eurUsdBarHistory.size() - 2);
            	InstrumentOHLC checkBar2 = eurUsdBarHistory.get(eurUsdBarHistory.size() - 3);
            	
            	printDebug("EURUSD", newBar, checkBar1, checkBar2);
            	
            	if ((newBar.getTickVolume() < checkBar1.getTickVolume()) &&  newBar.getTickVolume() < checkBar2.getTickVolume()) {
            		// possible no supply/demand following high volume bar
            		System.out.println("EURUSD possible no supply/demand following high volume bar");
            	}
            }
            if (eurUsdHighVolBarPresent && eurBarsSinceHighVol > 20) {
            	// reset everything and wait for another high volumme bar
            	eurUsdHighVolBarPresent = false;
            	eurBarsSinceHighVol = 0;
            	eurBarCount = 0;
            	System.out.println("EURUSD resetting HIGH volume after 20 bars");
            }
        }
        
     // process for GBPUSD 
        if (newBar.getInstrument().equals("GBPUSD")) {
        	gbpUsdBarHistory.add(newBar);
        	gbpBarCount++;
        	//System.out.println("GBPUSD bar count " + gbpBarCount);
        	
        	if (gbpUsdHighVolBarPresent) {
        		gbpBarsSinceHighVol++;
        	}
        	
            if (newBar.getTickVolume() > GBPUSD_VOL_LIMIT) {
            	gbpUsdHighVolBarPresent = true;	             
            	java.awt.Toolkit.getDefaultToolkit().beep();
            	System.out.println("GBPUSD Volume Alert");
            }
            
            if (gbpUsdHighVolBarPresent && gbpBarsSinceHighVol > 2) {
            	// check for no demand/supply in previous 2 bars
            	InstrumentOHLC checkBar1 = gbpUsdBarHistory.get(gbpUsdBarHistory.size()-2);
            	InstrumentOHLC checkBar2 = gbpUsdBarHistory.get(gbpUsdBarHistory.size()-3);
            	
            	printDebug("GBPUSD", newBar, checkBar1, checkBar2);
            	
            	if ((newBar.getTickVolume() < checkBar1.getTickVolume()) &&  newBar.getTickVolume() < checkBar2.getTickVolume()) {
            		// possible no supply/demand following high volume bar
            		System.out.println("GBPUSD possible no supply/demand following high volume bar");
            	}
            }
            
            if (gbpUsdHighVolBarPresent && gbpBarsSinceHighVol > 20) {
            	// reset everything and wait for another high volumme bar
            	gbpUsdHighVolBarPresent = false;
            	gbpBarsSinceHighVol = 0;
            	gbpBarCount = 0;
            	System.out.println("GBPUSD resetting HIGH volume after 20 bars");
            }
        }
        
        // process for GOLD 
        if (newBar.getInstrument().equals("GOLD")) {
        	goldBarHistory.add(newBar);
        	goldBarCount++;
        	//System.out.println("GOLD bar count " + goldBarCount);
        	
        	if (goldHighVolBarPresent) {
        		goldBarsSinceHighVol++;
        	}
        	
            if (newBar.getTickVolume() > GOLD_VOL_LIMIT) {
            	goldHighVolBarPresent = true;	             
            	java.awt.Toolkit.getDefaultToolkit().beep();
            	System.out.println("GOLD Volume Alert");
            }
            if (goldHighVolBarPresent && goldBarsSinceHighVol > 2) {
            	// check for no demand/supply in previous 2 bars
            	InstrumentOHLC checkBar1 = goldBarHistory.get(goldBarHistory.size()-2);
            	InstrumentOHLC checkBar2 = goldBarHistory.get(goldBarHistory.size()-3);
            	
            	printDebug("GOLD", newBar, checkBar1, checkBar2);
            	
            	if ((newBar.getTickVolume() < checkBar1.getTickVolume()) &&  newBar.getTickVolume() < checkBar2.getTickVolume()) {
            		// possible no supply/demand following high volume bar
            		System.out.println("GOLD possible no supply/demand following high volume bar");
            	}
            }
            if (goldHighVolBarPresent && goldBarsSinceHighVol > 20) {
            	// reset everything and wait for another high volumme bar
            	goldHighVolBarPresent = false;
            	goldBarsSinceHighVol = 0;
            	goldBarCount = 0;
            	System.out.println("GOLD resetting HIGH volume after 20 bars");
            }
        }
	}
	
	private boolean checkEuroForStrength(List<InstrumentOHLC> eurUsdBarHistory) {
	/*	
		if (newBar.getTickVolume() > EURUSD_VOL_LIMIT) { 
        	java.awt.Toolkit.getDefaultToolkit().beep();
        	System.out.println("EURUSD Volume Alert");
    	}
		& newBar.getTickVolume() > GBPUSD_VOL_LIMIT) {
                	java.awt.Toolkit.getDefaultToolkit().beep();
                	System.out.println("GBPUSD Volume Alert");
	 
                	& newBar.getTickVolume() > GOLD_VOL_LIMIT) {
                	java.awt.Toolkit.getDefaultToolkit().beep();
                	System.out.println("GOLD Volume Alert");
      */          	
		int lookbackLength = 20;
		int volCount = 0;
		
		List<InstrumentOHLC> subList = eurUsdBarHistory.subList(eurUsdBarHistory.size()-lookbackLength, eurUsdBarHistory.size());
		// look back through the barHistory to see if there is a high volume bar followed by a no supply
			
		for(InstrumentOHLC i:subList) {
			
			if(i.getTickVolume() > EURUSD_VOL_LIMIT) {
				volCount++;
				
			}
	          
	    }  
		
		return true;
	}
}
