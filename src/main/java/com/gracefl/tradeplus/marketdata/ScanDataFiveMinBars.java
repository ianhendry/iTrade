package com.gracefl.tradeplus.marketdata;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Example;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.gracefl.tradeplus.config.WebConfigurer;
import com.gracefl.tradeplus.domain.Instrument;
import com.gracefl.tradeplus.domain.PriceDataHistory;
import com.gracefl.tradeplus.domain.SignalService;
import com.gracefl.tradeplus.domain.TradeSignals;
import com.gracefl.tradeplus.domain.enumeration.BARCLOSE;
import com.gracefl.tradeplus.domain.enumeration.SIGNALBARSIZE;
import com.gracefl.tradeplus.domain.enumeration.SIGNALINDICATES;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;
import com.gracefl.tradeplus.repository.InstrumentRepository;
import com.gracefl.tradeplus.repository.SignalServiceRepository;
import com.gracefl.tradeplus.service.RunVolumeScanService;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ScanDataFiveMinBars {
	
	@Autowired
	private RunVolumeScanService runVolumeScan;
	
	@Autowired
	private InstrumentRepository instrumentRepository;
	
	@Autowired
	private SignalServiceRepository signalServiceRepository;
	
	@Autowired
	private HistoryBarHelper historyBarHelper;
	
	@Autowired
	TimeFrameHelper timeFrameHelper;
	 
	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);
	
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
    
	static List<Instrument> highVolBarPresent = new ArrayList<>();
	
	private static List<InstrumentOHLC> eurUsdBarHistory = new ArrayList<>();
	private static List<InstrumentOHLC> gbpUsdBarHistory = new ArrayList<>();  
	private static List<InstrumentOHLC> goldBarHistory = new ArrayList<>();  

	Set<SignalService> signalServiceSet = new HashSet<SignalService>();
	
	//Instrument pair = null;
	
    //TODO check Point in MT4 for GOLD
    //double point = 0.00001; // this is for currency pairs but not gold
    //double averageSpread = 0;
    //double averageVolume = 0;
    //double spreadHL = 0;
    //double middleOfBar = 0; 
	//double upOfBar = 0;    
	//double downOfBar = 0;
	
	//InstrumentOHLC b1 = null;
	//InstrumentOHLC b2 = null;
	//InstrumentOHLC b3 = null;
	//InstrumentOHLC b4 = null;
	
	public void scanBar (InstrumentOHLC newBar) {
		
		TradeSignals tradeSignal = null; 
		Instrument instrument = new Instrument();
		instrument.setTicker(newBar.getInstrument());
		
		Example<Instrument> instrumentExample = Example.of(instrument);
		 
		Optional<Instrument> returnedPairs = instrumentRepository.findOne(instrumentExample);
		
		// check we only got one then extract it to object
		if (returnedPairs.isPresent()) {

			instrument = returnedPairs.get();
			log.debug("Scan data matches ticker from MT4 to " + instrument.toString());		
			// figure out which pair came in
			if (newBar.getInstrument().equals("EURUSD")) {
				eurBarCount++;
				eurUsdBarHistory.add(newBar);
				
				if (highVolBarPresent.contains(instrument)) {
					eurBarsSinceHighVol++;
				}
				
				if (newBar.getTickVolume() > EURUSD_VOL_LIMIT && eurBarsSinceHighVol == 0) {
					highVolBarPresent.add(instrument);	             
		        	log.debug("EURUSD Volume Alert");
		        }
				
				if (eurUsdBarHistory.size() >= 4) {
					log.debug("Passed the 4 null checks");
					tradeSignal = runVolumeScan.runVolumeScan(eurUsdBarHistory, instrument);
				}
				if (highVolBarPresent.contains(instrument) && eurBarsSinceHighVol > EURUSD_VSA_SEQUENCE_LIMIT) {
		        	// reset everything and wait for another high volume bar
					highVolBarPresent.remove(highVolBarPresent.indexOf(instrument));
		        	eurBarsSinceHighVol = 0;
		        	eurBarCount = 0;
		        	log.debug("EURUSD resetting HIGH volume after 20 bars");
		        }	
			} else if (newBar.getInstrument().equals("GBPUSD")) {
				gbpBarCount++;
				gbpUsdBarHistory.add(newBar);
				
				if (highVolBarPresent.contains(instrument)) {
					gbpBarsSinceHighVol++;
				}
				
				if (newBar.getTickVolume() > GBPUSD_VOL_LIMIT) {
					highVolBarPresent.add(instrument);	             
		        	log.debug("GBPUSD Volume Alert");
		        }
				
				if (gbpUsdBarHistory.size() >= 4) {
					log.debug("Passed the 4 null checks");
					tradeSignal = runVolumeScan.runVolumeScan(gbpUsdBarHistory, instrument);
				}
				
				if (highVolBarPresent.contains(instrument) && gbpBarsSinceHighVol > GBPUSD_VSA_SEQUENCE_LIMIT) {
		        	// reset everything and wait for another high volumme bar
					highVolBarPresent.remove(highVolBarPresent.indexOf(instrument));
		        	gbpBarsSinceHighVol = 0;
		        	gbpBarCount = 0;
		        	log.debug("GBPUSD resetting HIGH volume after 20 bars");
		        }	
				
			} else if (newBar.getInstrument().equals("GOLD")) {
				goldBarCount++;
				goldBarHistory.add(newBar);
				
				if (highVolBarPresent.contains(instrument)) {
					goldBarsSinceHighVol++;
				}
				
				if (newBar.getTickVolume() > GOLD_VOL_LIMIT) {
					highVolBarPresent.add(instrument);	             
		        	log.debug("GOLD Volume Alert");
		        }
				
				if (goldBarHistory.size() > 4) {
					log.debug("Passed the 4 null checks");
					tradeSignal = runVolumeScan.runVolumeScan(goldBarHistory, instrument);
				}
				
				if (highVolBarPresent.contains(instrument) && goldBarsSinceHighVol > GOLD_VSA_SEQUENCE_LIMIT) {
		        	// reset everything and wait for another high volumme bar
					highVolBarPresent.remove(highVolBarPresent.indexOf(instrument));
		        	goldBarsSinceHighVol = 0;
		        	goldBarCount = 0;
		        	log.debug("GOLD resetting HIGH volume after 20 bars");
		        }	
				
			}
		        	
			//TODO
			// run a clean up to e.g. only keep the last 30 bars in the history by removing the oldest bar from the ArrayList 
			
		} else {
			log.debug("Scan data cannot find ticker " + newBar.getInstrument() + " in the database");
		}
		
		// persist the recived bar in history data
		PriceDataHistory newEntity = historyBarHelper.persistReceivedBarData(newBar, instrument);
		
		if (tradeSignal != null) {
			
			log.debug("Saving the trade signal");
			
			SignalService signalService = new SignalService();
			ZonedDateTime nowDate = ZonedDateTime.now(ZoneId.systemDefault());
			
			signalService.setTimeframe(timeFrameHelper.getTimeFrameFromPeriod(newBar.getPeriod()));
			signalService.setAlertDate(LocalDate.now());
			signalService.setAlertTime(nowDate);
			signalService.setTicker(newBar.getInstrument());
			signalService.setAlertPrice(newBar.getClosePrice());
			signalService.setAlertText(tradeSignal.getName());
			signalService.setAlertDescription(tradeSignal.getDescription());
			signalService.setBarClose(getBarClose(newBar));
			signalService.setSignalIndicates(tradeSignal.getSignalIndicates());
			signalService.setBarSize(getBarSize(newBar));
			signalService.setTradeSignals(tradeSignal);
			signalService.setPriceDataHistory(newEntity);
			signalService.setBarVolume(new BigDecimal(newBar.getTickVolume()));
			signalService.setIsPublished(false);
			
			signalServiceRepository.saveAndFlush(signalService);
		} 
		
		log.debug("ScanBar 5MIN eurUsdBarHistory size = " + eurUsdBarHistory.size());
		log.debug("ScanBar 5MIN gbpUsdBarHistory size = " + gbpUsdBarHistory.size());
		log.debug("ScanBar 5MIN goldBarHistory size = " + goldBarHistory.size());
		
	}
    
	private SIGNALBARSIZE getBarSize(InstrumentOHLC newBar) {
		
		return SIGNALBARSIZE.GIGANTIC;
	}
	
	private BARCLOSE getBarClose(InstrumentOHLC newBar) {
		
		BARCLOSE barclose=null; 
		
		double MIDDLEOFBAR   = (newBar.getHighPrice()+newBar.getLowPrice())/2;                             							// EXACT MIDDLE 
		//double UPOFBAR       = (newBar.getHighPrice()+newBar.getLowPrice())/2 + (newBar.getHighPrice()-newBar.getLowPrice())/3.6;  // UP CLOSE    
		//double DOWNOFBAR     = (newBar.getHighPrice()+newBar.getLowPrice())/2 - (newBar.getHighPrice()-newBar.getLowPrice())/3.6;  // DOWN CLOSE 
		
		if (newBar.getClosePrice() > MIDDLEOFBAR) {
			barclose = BARCLOSE.TOP; 
		} 
		if (newBar.getClosePrice() < MIDDLEOFBAR) {
			barclose = BARCLOSE.LOW; 
		}
		return barclose;
	}
}