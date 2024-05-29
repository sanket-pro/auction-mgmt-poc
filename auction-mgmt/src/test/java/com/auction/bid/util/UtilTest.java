package com.auction.bid.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class UtilTest {

	int offset =1;
	
	@Test
	public void parsetoCalendarTest() {
		
		String month = "05";
		String date = "29";
		String year = "2024";
		
		
		
		Calendar calDate = Util.parsetoCalendar(month+date+year);
		assertEquals(Integer.valueOf(month)-offset, calDate.get(Calendar.MONTH));
		assertEquals(Integer.valueOf(date), calDate.get(Calendar.DATE));
		assertEquals(Integer.valueOf(year), calDate.get(Calendar.YEAR));
	}
	
	@Test
	public void parsetoCalendarBoundaryTest1() {
		
		String month = "01";
		String date = "01";
		String year = "2024";
		
		Calendar calDate = Util.parsetoCalendar(month+date+year);
		assertEquals(Integer.valueOf(month)-offset, calDate.get(Calendar.MONTH));
		assertEquals(Integer.valueOf(date), calDate.get(Calendar.DATE));
		assertEquals(Integer.valueOf(year), calDate.get(Calendar.YEAR));
	}
	
	@Test
	public void parsetoCalendarBoundaryTest2() {
		
		String month = "12";
		String date = "31";
		String year = "2024";
		
		Calendar calDate = Util.parsetoCalendar(month+date+year);
		assertEquals(Integer.valueOf(month)-offset, calDate.get(Calendar.MONTH));
		assertEquals(Integer.valueOf(date), calDate.get(Calendar.DATE));
		assertEquals(Integer.valueOf(year), calDate.get(Calendar.YEAR));
	}
}
