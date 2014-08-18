package com.ssf.edog.service;

import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

public class SwitchService extends Service {

	private SharedPreferenceUtil mPreferenceUtil;

	public SwitchService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		switch (mPreferenceUtil.getType()) {

		case SharedPreferenceUtil.AUTO_ON_OFF:

			break;
		case SharedPreferenceUtil.AUTO_REBOOT:

			break;
		case SharedPreferenceUtil.AUTO_OFF:

			break;

		default:
			break;
		}

		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}
}
