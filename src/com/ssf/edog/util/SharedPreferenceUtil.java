package com.ssf.edog.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

	/**
	 * ���ӹ����ص�key
	 */
	private static final String SWITCH = "switch";

	/**
	 * ���ӹ����ص�Ĭ��״̬
	 */
	private static final boolean DEFAULT_SWITCH = false;

	/**
	 * ���ڱ������ݵ��ļ���
	 */
	private static final String FILE_NAME = "edog";

	/**
	 * ����Ա��������Ӧ��key
	 */
	public static final String PWD_KEY = "pwd";

	/**
	 * ���ӹ���̽ʱ��������Ӧ��key
	 */
	public static final String INTERVAL_KEY = "interval";

	/**
	 * Ĭ�ϵĹ���Ա����
	 */
	private static final String DEFAULT_PWD = "123456";

	/**
	 * Ĭ�ϵĵ��ӹ���̽ʱ����
	 */
	private static final int DEFAULT_INTERVAL = 3;

	private SharedPreferences mSharedPreferences;

	public SharedPreferenceUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * �������Ա���뵽�ļ���
	 * 
	 * @param pwd
	 */
	public void savePassword(String pwd) {

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(PWD_KEY, pwd);
		editor.commit();

	}

	/**
	 * ���ļ���ù���Ա����
	 * 
	 * @return
	 */
	public String getPassword() {
		return mSharedPreferences.getString(PWD_KEY, DEFAULT_PWD);
	}

	/**
	 * ������ӹ���̽ʱ�������ļ���
	 * 
	 * @param interval
	 */
	public void saveInterval(int interval) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(INTERVAL_KEY, interval);
		editor.commit();
	}

	/**
	 * ���ļ��л�õ��ӹ���̽ʱ����
	 * 
	 * @return
	 */
	public int getInterval() {
		return mSharedPreferences.getInt(INTERVAL_KEY, DEFAULT_INTERVAL);
	}

	/**
	 * ���ڱ�����ӹ��Ŀ���״̬
	 * 
	 * @param is
	 */
	public void saveSwitch(boolean is) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(SWITCH, is);
		editor.commit();
	}

	/**
	 * �õ����ӹ��Ŀ���״̬
	 * 
	 * @return
	 */
	public boolean isSwitch() {
		return mSharedPreferences.getBoolean(SWITCH, DEFAULT_SWITCH);
	}

	/**
	 * ����ļ��б��������
	 */
	public void clear() {
		mSharedPreferences.edit().clear().commit();
	}

}
