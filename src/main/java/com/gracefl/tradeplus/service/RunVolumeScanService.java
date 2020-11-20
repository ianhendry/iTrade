package com.gracefl.tradeplus.service;

import java.util.List;
//import java.util.HashSet;
import java.util.Optional;
//import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gracefl.tradeplus.domain.Instrument;
import com.gracefl.tradeplus.domain.TradeSignals;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;
import com.gracefl.tradeplus.repository.TradeSignalsRepository;

@Service
//@Transactional
public class RunVolumeScanService {

    private final Logger log = LoggerFactory.getLogger(RunVolumeScanService.class);

    @Autowired
    private TradeSignalsRepository tradeSignalsRepository;
	
	public TradeSignals runVolumeScan(List<InstrumentOHLC> barHistory, Instrument pair ) {
	
		log.debug("ENTER volume scan for " + pair.getTicker());	
		
		double MIDDLEOFBAR;
		double UPOFBAR;
		double DOWNOFBAR;
		double point = 0.00001; // this is for currency pairs but not gold
	    double averageSpread = 0;
	    double averageVolume = 0;
	    double spreadHL = 0;
	    //double middleOfBar = 0; 
		//double upOfBar = 0;    
		//double downOfBar = 0;
		
		InstrumentOHLC b1 = barHistory.get(barHistory.size()-1);
		InstrumentOHLC b2 = barHistory.get(barHistory.size()-2);
		InstrumentOHLC b3 = barHistory.get(barHistory.size()-3);
		InstrumentOHLC b4 = barHistory.get(barHistory.size()-4);;

		MIDDLEOFBAR   = (b1.getHighPrice()+b1.getLowPrice())/2;                             // EXACT MIDDLE 
		UPOFBAR       = (b1.getHighPrice()+b1.getLowPrice())/2 + (b1.getHighPrice()-b1.getLowPrice())/3.6;  // UP CLOSE    
		DOWNOFBAR     = (b1.getHighPrice()+b1.getLowPrice())/2 - (b1.getHighPrice()-b1.getLowPrice())/3.6;  // DOWN CLOSE  
		
		for (int i = 0; i < barHistory.size(); i++) {
			averageVolume = averageVolume + barHistory.get(i).getTickVolume();  
			averageSpread = averageSpread + (b2.getHighPrice() - b2.getLowPrice())/point;
		}

	    averageVolume = averageVolume/barHistory.size();
	    spreadHL = (b2.getHighPrice() - b2.getLowPrice())/point;   
	    averageSpread = averageSpread/barHistory.size();
	    
		TradeSignals tradeSignal = null;
		
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume && b2.getTickVolume() > averageVolume) {
			tradeSignal = findTradeSignals("REGULAR UPTHRUST");
			log.debug(pair.getTicker() + " REGULAR UPTHRUST / Weakness - SM marked up the prices indicating strong bullishness. Get ready for MarkDown. ");
		}
		         
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume*2) {
			tradeSignal = findTradeSignals("HIGH VOLUME UPTHRUST");
			log.debug(pair.getTicker() + " HIGH VOLUME UPTHRUST / Weakness - SM marked up the prices indicating strong bullishness. Get ready for MarkDown. ");
		}
			     
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL > averageSpread*1.8 && b2.getClosePrice() > DOWNOFBAR  && b2.getClosePrice() < UPOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume) {
			tradeSignal = findTradeSignals("UNSUCESSFUL UPTHRUST");
			log.debug(pair.getTicker() + " UNSUCESSFUL UPTHRUST / Demand stronger - SM was not successful in marking the price down. There was too much demand. ");  //
		}
		    
		if (b2.getHighPrice()>b3.getHighPrice() && spreadHL < averageSpread && b2.getClosePrice() < DOWNOFBAR && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() < averageVolume) {
			tradeSignal = findTradeSignals("PSEUDO UPTHRUST");
			log.debug(pair.getTicker() + " PSEUDO UPTHRUST / Weakness - Upthrusts with low volume. Sign of weakness  ");  //
		}
			   
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL < averageSpread*0.8 && b2.getTickVolume() < averageVolume) {
			tradeSignal = findTradeSignals("NO DEMAND");
			log.debug(pair.getTicker() + " NO DEMAND / Weakness - No support from the SM. The SM is not interested in higher prices.  "); //
		}
		    
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() > UPOFBAR && spreadHL > averageSpread*1.5 && b2.getTickVolume() > b3.getTickVolume()) {
			tradeSignal = findTradeSignals("MARK UP");
			log.debug(pair.getTicker() + " MARK UP / Weakness - Effort to move up. SM will test the market for supply before trying to move up further. ");  //
		}
		    
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL > averageSpread*1.5 && b2.getTickVolume() > b3.getTickVolume() && b2.getTickVolume() > averageVolume) {
			tradeSignal = findTradeSignals("FAILED MARK UP");
			log.debug(pair.getTicker() + " FAILED MARK UP / Strenght - Effort to move up. SM will test the market for supply before trying to move up further.  ");  //
		}
			 
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() > UPOFBAR && b2.getTickVolume() < b3.getTickVolume() && b2.getTickVolume() < averageVolume) {
			tradeSignal = findTradeSignals("TESTING FOR SUPPLY");
			log.debug(pair.getTicker() + " TESTING FOR SUPPLY / Strenght - Marking down the price. Low volume or less trading activity indicates a successful test. ");  //
		}
			  
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() > MIDDLEOFBAR && b2.getTickVolume() > averageVolume) {
			tradeSignal = findTradeSignals("STOPPING VOLUME");
			log.debug(pair.getTicker() + " STOPPING VOLUME / Strength - SM is absorbing the price. SM has decided to stop the down tide and start accumulating. ");  //
		}
			 
		if (b2.getClosePrice() > b3.getClosePrice() && b2.getClosePrice() > MIDDLEOFBAR && b2.getLowPrice() < b3.getLowPrice() && b3.getLowPrice() < b4.getLowPrice() && spreadHL > averageSpread*1.5 && b2.getTickVolume() > averageVolume) {
			tradeSignal = findTradeSignals("RREVERSE UPTHRUST");
			log.debug(pair.getTicker() + " REVERSE UPTHRUST / Strength - This is a good sign of strength returning and you find the trend reversing almost immediately. ");  //
		}
			
		if (b2.getClosePrice() < b3.getClosePrice() && b2.getClosePrice() < MIDDLEOFBAR && spreadHL < averageSpread*0.8 && b1.getTickVolume() < averageVolume) {
			tradeSignal = findTradeSignals("NO SUPPLY");
			log.debug(pair.getTicker() + " NO SUPPLY / Strength - In up trend are indications of continued trend. Strength, especially if they appear before/after test bars. ");  //
		}
	 
		//log.debug(pair + " volume scan END");
		
		return tradeSignal;
	}

	private TradeSignals findTradeSignals(String signalName) {
		
		TradeSignals ts = new TradeSignals();
		ts.setName(signalName);
		
		Example<TradeSignals> tsExample = Example.of(ts);
		 
		Optional<TradeSignals> tradeSignal = tradeSignalsRepository.findOne(tsExample);
		
		if (tradeSignal.isPresent()) {
			return tradeSignal.get();
		} else return null;
		
	}
	
}
