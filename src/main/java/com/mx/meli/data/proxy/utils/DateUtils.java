package com.mx.meli.data.proxy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/** David Garcia Isario
 * 
 */
@Slf4j
public class DateUtils {
	
	private DateUtils() {
		
	}

	public static String getDateNow( String format ) {
	
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat( format );  
		return dateFormat.format(date);  
		
	}

	public static String getStringByDate( Date dateToString, String format ) {
		
		DateFormat dateFormat = new SimpleDateFormat( format );  
		return dateFormat.format( dateToString );  
		
	}
	
	
	public static Date getDateByString( String stringTodate, String format ) {
		
		DateFormat dateFormat = new SimpleDateFormat( format );  
		Date date = null; 
		
		try {
			date = dateFormat.parse(stringTodate); 
		} catch (ParseException e) {
			log.error("The string cant to convert to date: {}", e);
		}
		
		return date;
	}
	
	public static int getWeekOfYear() {
		
		ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

		return now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
	}
	
}
