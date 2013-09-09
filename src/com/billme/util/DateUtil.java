package com.billme.util;

import android.text.format.Time;

public class DateUtil {
	
	public static String getDate(){
		Time time = new Time();
		time.setToNow();
		return time.format("%m/%d %H:%M");
	}
}
