package com.gracefl.tradeplus.zeromq;


import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;

public class TimeFrameHelper {

	public TIMEFRAME getTimeFrameFromPeriod(String period) {
		
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
