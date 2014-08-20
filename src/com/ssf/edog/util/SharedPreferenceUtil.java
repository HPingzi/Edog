package com.ssf.edog.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

	/**
	 * 电子狗开关的key
	 */
	private static final String SWITCH = "switch";

	/**
	 * 电子狗开关的默认状态
	 */
	private static final boolean DEFAULT_SWITCH = false;

	/**
	 * 用于保存数据的文件名
	 */
	private static final String FILE_NAME = "edog";

	/**
	 * 管理员密码所对应的key
	 */
	public static final String PWD_KEY = "pwd";

	/**
	 * 电子狗嗅探时间间隔所对应的key
	 */
	public static final String INTERVAL_KEY = "interval";

	/**
	 * 默认的管理员密码
	 */
	private static final String DEFAULT_PWD = "123456";

	/**
	 * 默认的电子狗嗅探时间间隔
	 */
	private static final int DEFAULT_INTERVAL = 3;

	public static final String ON_HOUR_KEY = "on_hour";
	public static final String ON_MINUTE_KEY = "on_minute";
	public static final String OFF_HOUR_KEY = "off_hour";
	public static final String OFF_MINUTE_KEY = "off_minute";
	public static final String REBOOT_HOUR_KEY = "reboot_hour";
	public static final String REBOOT_MINUTE_KEY = "reboot_minute";
	public static final String TYPE_KEY = "type";
	public static final String IS_ENABLE_KEY = "is_enable_key";

	public static final int CLOSE_SETTING = 0;
	public static final int AUTO_ON_OFF = 1;
	public static final int AUTO_REBOOT = 2;
	public static final int AUTO_OFF = 3;

	private SharedPreferences mSharedPreferences;

	public SharedPreferenceUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
	}

	public void setOnHour(int hour) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(ON_HOUR_KEY, hour);
		editor.commit();
	}

	public int getOnHour() {
		return mSharedPreferences.getInt(ON_HOUR_KEY, 0);
	}

	public void setOnMinute(int minute) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(ON_MINUTE_KEY, minute);
		editor.commit();
	}

	public int getOnMinute() {
		return mSharedPreferences.getInt(ON_MINUTE_KEY, 0);
	}

	public void setOffHour(int hour) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(OFF_HOUR_KEY, hour);
		editor.commit();
	}

	public int getOffHour() {
		return mSharedPreferences.getInt(OFF_HOUR_KEY, 0);
	}

	public void setOffMinute(int minute) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(OFF_MINUTE_KEY, minute);
		editor.commit();
	}

	public int getOffMinute() {
		return mSharedPreferences.getInt(OFF_MINUTE_KEY, 0);
	}

	public void setRebootHour(int hour) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(REBOOT_HOUR_KEY, hour);
		editor.commit();
	}

	public int getRebootHour() {
		return mSharedPreferences.getInt(REBOOT_HOUR_KEY, 0);
	}

	public void setRebootMinute(int minute) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(REBOOT_MINUTE_KEY, minute);
		editor.commit();
	}

	public int getRebootMinute() {
		return mSharedPreferences.getInt(REBOOT_MINUTE_KEY, 0);
	}

	public void setType(int type) {

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(TYPE_KEY, type);
		editor.commit();

	}

	public int getType() {
		return mSharedPreferences.getInt(TYPE_KEY, CLOSE_SETTING);
	}

	public void setEnable(boolean enable) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(IS_ENABLE_KEY, enable);
		editor.commit();
	}

	public boolean isEnable() {
		return mSharedPreferences.getBoolean(IS_ENABLE_KEY, false);
	}

	/**
	 * 保存管理员密码到文件中
	 * 
	 * @param pwd
	 */
	public void savePassword(String pwd) {

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(PWD_KEY, pwd);
		editor.commit();

	}

	/**
	 * 从文件获得管理员密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return mSharedPreferences.getString(PWD_KEY, DEFAULT_PWD);
	}

	/**
	 * 保存电子狗嗅探时间间隔到文件中
	 * 
	 * @param interval
	 */
	public void saveInterval(int interval) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(INTERVAL_KEY, interval);
		editor.commit();
	}

	/**
	 * 从文件中获得电子狗嗅探时间间隔
	 * 
	 * @return
	 */
	public int getInterval() {
		return mSharedPreferences.getInt(INTERVAL_KEY, DEFAULT_INTERVAL);
	}

	/**
	 * 用于保存电子狗的开关状态
	 * 
	 * @param is
	 */
	public void saveSwitch(boolean is) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(SWITCH, is);
		editor.commit();
	}

	/**
	 * 得到电子狗的开关状态
	 * 
	 * @return
	 */
	public boolean isSwitch() {
		return mSharedPreferences.getBoolean(SWITCH, DEFAULT_SWITCH);
	}

	/**
	 * 清除文件中保存的数据
	 */
	public void clear() {
		mSharedPreferences.edit().clear().commit();
	}

}
