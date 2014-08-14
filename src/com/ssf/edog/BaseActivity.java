package com.ssf.edog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected void openActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	protected void openActivity(Class<?> clazz, Bundle bundle) {

		Intent intent = new Intent(this, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
	}

}
