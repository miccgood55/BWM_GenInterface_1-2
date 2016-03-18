package com.wmsl.utils;

import java.util.Calendar;

public class GenDataDBUtils {
	public static Calendar getCalendar(int year, int month, int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar;
	}
}
