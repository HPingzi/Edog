package com.ssf.edog.receiver;

import com.ssf.edog.service.EdogService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	public BootReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			context.startService(new Intent(context, EdogService.class));

		}
	}
}
