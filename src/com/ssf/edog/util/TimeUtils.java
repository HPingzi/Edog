package com.ssf.edog.util;

import java.util.Calendar;

public class TimeUtils {


	/**
	 * 计算从现在到
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static long calculateRebootTime(int hour, int minute) {

		Calendar calendar = Calendar.getInstance();
		long nowTime = calendar.getTimeInMillis();

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 1);
		long rebootTime = calendar.getTimeInMillis();

		if (rebootTime - nowTime > 0) {
			return rebootTime;
		}

		return rebootTime + 24 * 60 * 60 * 1000;
	}

}
