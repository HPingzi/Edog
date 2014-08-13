package com.ssf.edog.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ssf.edog.config.Config;
import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.IBinder;

public class EdogService extends Service {
	private String mCurrentPackageName;
	private PackageManager mPackageManager;
	private ActivityManager mActivityManager;
	private SharedPreferenceUtil mPreferenceUtil;
	private ScheduledExecutorService mExecutorService;

	@Override
	public void onCreate() {
		super.onCreate();
		mPackageManager = getPackageManager();
		mCurrentPackageName = getPackageName();
		mPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mExecutorService = Executors.newScheduledThreadPool(1);
		mExecutorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				// 得到当前正在前台运行的应用程序包名
				String runingBagName = mActivityManager.getRunningTasks(1).get(
						0).topActivity.getPackageName();

				// 如果当前所要监听的程序正在前台运行则不用执行后续代码

				if (runingBagName.equals(Config.PACKAGE_NAME)) {
					return;
				}

				// 如果当前在前台运行的程序是电子狗则不用执行后续代码
				if (runingBagName.equals(mCurrentPackageName)) {
					return;
				}

				// 所要监听的程序不在前台运行，切换到该程序

				Intent intent = mPackageManager
						.getLaunchIntentForPackage(Config.PACKAGE_NAME);

				startActivity(intent);

			}
		}, 0, mPreferenceUtil.getInterval(), TimeUnit.SECONDS);

		return super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mExecutorService.shutdown();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
