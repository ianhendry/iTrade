package com.gracefl.tradeplus.marketdata;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gracefl.tradeplus.domain.Instrument;
import com.gracefl.tradeplus.domain.PriceDataHistory;
import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;
import com.gracefl.tradeplus.helpers.json.InstrumentOHLC;
import com.gracefl.tradeplus.repository.PriceDataHistoryRepository;

@Service
@Transactional
public class HistoryBarHelper {

    @Autowired
    private PriceDataHistoryRepository priceDataHistoryRepository;
    
    @Autowired
    TimeFrameHelper timeFrameHelper;
    
	public PriceDataHistory persistReceivedBarData(InstrumentOHLC newBar, Instrument instrument) {
		
		PriceDataHistory priceDataHistory = new PriceDataHistory();
		
		TIMEFRAME timeframe = timeFrameHelper.getTimeFrameFromPeriod(newBar.getPeriod());

		ZoneId id = ZoneId.of("UT");
		Long longDate = new Long(newBar.getBarTime()) * 1000;
		Instant myinstant = Instant.ofEpochMilli(longDate);
		ZonedDateTime zonedDateTime = myinstant.atZone(id);
		
		priceDataHistory.setInstrument(instrument);
		priceDataHistory.setPriceClose(newBar.getClosePrice());
		priceDataHistory.setPriceDate(LocalDate.now());
		priceDataHistory.setPriceTime(zonedDateTime);
		priceDataHistory.setPriceHigh(newBar.getHighPrice());
		priceDataHistory.setPriceLow(newBar.getLowPrice());
		priceDataHistory.setPriceOpen(newBar.getOpenPrice());
		priceDataHistory.setPriceTimeframe(timeframe);
		priceDataHistory.setPriceVolume(newBar.getTickVolume().doubleValue());
		
		// save the entity via some service helper
		PriceDataHistory newEntity = priceDataHistoryRepository.saveAndFlush(priceDataHistory);
		
		return newEntity;
		
	}
	
}
