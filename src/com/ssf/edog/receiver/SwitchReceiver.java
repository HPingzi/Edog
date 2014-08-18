package com.ssf.edog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ssf.edog.config.Config;
import com.ssf.edog.util.SharedPreferenceUtil;

public class SwitchReceiver extends BroadcastReceiver {

	private SharedPreferenceUtil mPreferenceUtil;

	@Override
	public void onReceive(Context context, Intent intent) {

		mPreferenceUtil = new SharedPreferenceUtil(context);

		if (intent.getAction().equals(Config.SWITCH_ACTION)) {

			switch (mPreferenceUtil.getType()) {
			case SharedPreferenceUtil.AUTO_REBOOT:

				Intent rebootIntent = new Intent(Intent.ACTION_REBOOT);
				rebootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(rebootIntent);

				break;

			case SharedPreferenceUtil.AUTO_OFF:
				Intent offIntent = new Intent(
						"android.intent.action.ACTION_REQUEST_SHUTDOWN");

				// 源码中"android.intent.action.ACTION_REQUEST_SHUTDOWN“ 就是
				// Intent.ACTION_REQUEST_SHUTDOWN方法
				offIntent.putExtra("android.intent.extra.KEY_CONFIRM", false);

				// 源码中"android.intent.extra.KEY_CONFIRM"就是
				// Intent.EXTRA_KEY_CONFIRM方法
				offIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(offIntent);
				break;

			default:
				break;
			}

		}

	}
}
