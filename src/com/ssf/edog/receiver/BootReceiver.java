package com.ssf.edog.receiver;

import com.ssf.edog.config.Config;
import com.ssf.edog.service.EdogService;
import com.ssf.edog.util.MachineUtil;
import com.ssf.edog.util.SharedPreferenceUtil;
import com.ssf.edog.util.TimeUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	private AlarmManager mAlarmManager;
	private SharedPreferenceUtil mPreferenceUtil;
	private MachineUtil mMachineUtil;

	public BootReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		mAlarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		mPreferenceUtil = new SharedPreferenceUtil(context);
		mMachineUtil = new MachineUtil();
		mMachineUtil.close();

		Intent newIntent = new Intent(Config.SWITCH_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			if (mPreferenceUtil.isSwitch()) {
				context.startService(new Intent(context, EdogService.class));
			}

			switch (mPreferenceUtil.getType()) {

			case SharedPreferenceUtil.AUTO_REBOOT:

				mAlarmManager.set(AlarmManager.RTC_WAKEUP, TimeUtils
						.calculateRebootTime(mPreferenceUtil.getRebootHour(),
								mPreferenceUtil.getRebootMinute()),
						pendingIntent);

				break;
			case SharedPreferenceUtil.AUTO_OFF:

				mAlarmManager.set(
						AlarmManager.RTC_WAKEUP,
						TimeUtils.calculateRebootTime(
								mPreferenceUtil.getOffHour(),
								mPreferenceUtil.getOffMinute()), pendingIntent);

				break;
			case SharedPreferenceUtil.AUTO_ON_OFF:

				int offHour = mPreferenceUtil.getOffHour();
				int offMinute = mPreferenceUtil.getOffMinute();
				int onHour = mPreferenceUtil.getOnHour();
				int onMinute = mPreferenceUtil.getOnMinute();

				mMachineUtil.setBoffh((byte) offHour);
				mMachineUtil.setBoffm((byte) offMinute);
				mMachineUtil.setBonh((byte) onHour);
				mMachineUtil.setBonm((byte) onMinute);

				mMachineUtil.openMachine();

				break;

			default:
				break;
			}

		}
	}
}
