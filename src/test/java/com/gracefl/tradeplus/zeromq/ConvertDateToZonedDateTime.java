package com.gracefl.tradeplus.zeromq;

import java.time.Instant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//import org.joda.time.DateTime;
//import org.joda.time.DateTimeZone;

public class ConvertDateToZonedDateTime {

	public static void main(String[] args) {

		/*
		 * String[] zones = TimeZone.getAvailableIDs();
		for (String s: zones)
        {
          System.out.println(s);
        }
		 */
		//Date date = new Date();
		//ZoneId defaultZoneId = ZoneId.systemDefault();
		//TimeZone tz = TimeZone.getTimeZone("Etc/GMT+2");
		// create a calendar
	      Calendar cal = Calendar.getInstance();

	      // print current time zone
	      String name = cal.getTimeZone().getDisplayName();
	      
	      System.out.println("Current Time Zone:" + name );
	      
	      
	      TimeZone tz = TimeZone.getTimeZone("HFE");
	      
	      cal.setTimeZone(tz);
	      
		TimeZone mt4ServerTimeZone = TimeZone.getTimeZone("HFE");
		ZoneId serverZoneId = mt4ServerTimeZone.toZoneId();
		
		Long longDate = new Long(1605837600) * 1000;
		Date date = new java.util.Date(longDate);
		
		String adate = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (longDate));
		System.out.println("Date : " + date);
		System.out.println("Date : " + adate);
		// Get system default time zone id.
		
		// Convert Date to Instant.
		Instant instant = date.toInstant();
		//Instant + default time zone.
		ZonedDateTime zonedDateTime = instant.atZone(serverZoneId);
		//System.out.println("ZonedDateTime : " + zonedDateTime);
		javaConvertTime();
	}
	
	private static void javaConvertTime() {
		ZoneId id = ZoneId.of("UT");
		Long longDate = new Long(1605837600) * 1000;
		//Date date = new java.util.Date(longDate);
		Instant myinstant = Instant.ofEpochMilli(longDate);
		ZonedDateTime zonedDateTime = myinstant.atZone(id);
		System.out.println("ZonedDateTime : " + zonedDateTime);
		
	}

	
	
}