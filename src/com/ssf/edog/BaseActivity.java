package com.ssf.edog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

	/**
	 * 打开新的Activity
	 * 
	 * @param clazz
	 */
	protected void openActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	/**
	 * 将数据封装在一个Bundle对象然后传递给要打开的Activity
	 * 
	 * @param clazz
	 * @param bundle
	 */
	protected void openActivity(Class<?> clazz, Bundle bundle) {

		Intent intent = new Intent(this, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
	}

}
