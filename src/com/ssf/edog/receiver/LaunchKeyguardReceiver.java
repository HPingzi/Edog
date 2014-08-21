package com.ssf.edog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ssf.edog.KeyguardActivity;
import com.ssf.edog.config.Config;

public class LaunchKeyguardReceiver extends BroadcastReceiver {
	public LaunchKeyguardReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Config.LAUNCH_KEYGUAGRD_ACTION)) {
			Intent newIntent = new Intent(context, KeyguardActivity.class);
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(newIntent);
		}
	}
}
