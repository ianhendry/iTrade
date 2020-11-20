package com.gracefl.tradeplus.zeromq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;

import net.minidev.json.writer.JsonReader;

public class VSAScanner {

	private static Random rand = new Random(System.nanoTime());
	final static Integer EURUSD_VOL_LIMIT = 1050;
	final static Integer GBPUSD_VOL_LIMIT = 1400;
	final static Integer GOLD_VOL_LIMIT = 4000;
	
	public static void main(String[] args) throws JsonMappingException, java.io.IOException {

        List<InstrumentOHLC> eurUsdBarHistory = new ArrayList<>();
        List<InstrumentOHLC> gbpUsdBarHistory = new ArrayList<>();  
        List<InstrumentOHLC> goldBarHistory = new ArrayList<>();  

        // load test data 
        BufferedReader reader;
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			reader = new BufferedReader(new FileReader("eurusd_data.json"));
			String line = reader.readLine();
			while (line != null) {
				InstrumentOHLC newBar = objectMapper.readValue(new File(""), InstrumentOHLC.class);
				eurUsdBarHistory.add(newBar);
				System.out.println(newBar);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 // perform scan
		
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
