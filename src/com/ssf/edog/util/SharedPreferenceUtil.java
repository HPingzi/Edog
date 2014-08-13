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

	private SharedPreferences mSharedPreferences;

	public SharedPreferenceUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
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
