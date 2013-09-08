package com.billme.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
		String s = dateFormat.format(new Date());
		return s;
	}
}
