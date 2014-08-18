package com.ssf.edog.util;

import java.util.Calendar;

public class TimeUtils {

	/**
	 * 设置开机时间跟关机时间
	 * 
	 * @param onHour
	 * @param onMinute
	 * @param offHour
	 * @param offMinute
	 */
	public void setOnOFFTime(int onHour, int onMinute, int offHour,
			int offMinute) {

	}

	/**
	 * 设置重启时间
	 * 
	 * @param rebootHour
	 * @param rebootMinute
	 */
	public void setRebootTime(int rebootHour, int rebootMinute) {

	}

	/**
	 * 设置关机时间
	 * 
	 * @param offHour
	 * @param offMinute
	 */
	public void setOffTime(int offHour, int offMinute) {

	}

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
