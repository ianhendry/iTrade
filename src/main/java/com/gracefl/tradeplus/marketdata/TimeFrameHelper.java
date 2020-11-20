package com.gracefl.tradeplus.marketdata;

import org.springframework.stereotype.Service;

import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;

@Service
public class TimeFrameHelper {

	public TIMEFRAME getTimeFrameFromPeriod(String period) {
		
		TIMEFRAME timeframe;
		
		switch(period)   {
			//case "M1": timeframe=TIMEFRAME.M1;
			case "M5": timeframe=TIMEFRAME.M5; break;
			case "M15": timeframe=TIMEFRAME.M15; break;
			case "M30": timeframe=TIMEFRAME.M30; break;
			case "H1": timeframe=TIMEFRAME.H1; break;
			case "H4": timeframe=TIMEFRAME.H4; break;
			case "D1": timeframe=TIMEFRAME.D1; break;
			case "W1": timeframe=TIMEFRAME.W1; break;
			case "M1": timeframe=TIMEFRAME.M1; break;
			default: timeframe=TIMEFRAME.M5;
		}
		
		return timeframe;
	}
	
}
