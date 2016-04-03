package com.wmsl.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class GenDataDBUtils {
	public static Calendar getCalendar(int year, int month, int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar;
	}
	
	public static List<Integer> getListInteger(int minValue, int maxValue){

		List<Integer> listAll = new ArrayList<Integer>();
		
		for (int i = minValue; i <= maxValue; i++) {
			listAll.add(new Integer(i));
		}
		
		return listAll;
	}

	public static List<Integer> getRandList(int maxRowData, int minValue, int maxValue){
		
		List<Integer> listAll = getListInteger(minValue, maxValue);
		
		Collections.shuffle(listAll);
		
		listAll = listAll.subList(0, maxRowData);
		Collections.sort(listAll);
		
		return listAll;
				
	}
	
}
