package com.auction.bid.util;

import java.util.Calendar;

public class Util {
	
	public static Calendar parsetoCalendar(String strDate) {
		Calendar calDate = Calendar.getInstance();
		int year = Integer.valueOf(strDate.substring(4));
		//reduce month by 1 since it month counter starts from 0-11 and user input considers 1-12
		int month = Integer.valueOf(strDate.substring(0,2)) -1;
		int date = Integer.valueOf(strDate.substring(2,4));
		calDate.set(year, month, date, 0, 0, 0);
		return calDate;
	}
	
	public static boolean isPastDate(String strDate) {
		Calendar calDate = Util.parsetoCalendar(strDate);
		Calendar calToday = Calendar.getInstance(); 
		
		//check if auction start date is less than current date and given date belongs to current year
		if(calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR) && 
				calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) && 
				calDate.get(Calendar.DAY_OF_MONTH) < calToday.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		// check if auction month is a past month of current year
		else if(calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR) && 
				calDate.get(Calendar.MONTH) < calToday.get(Calendar.MONTH)) {
			return true;
		}
		
		//check if auction start date is in past year
		if(calDate.get(Calendar.YEAR) < calToday.get(Calendar.YEAR)) {
			return true;
		}
		return false;
	}

}
