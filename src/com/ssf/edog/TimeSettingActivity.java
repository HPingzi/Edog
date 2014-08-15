package com.ssf.edog;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class TimeSettingActivity extends Activity {

	private TimeSettingFragment mSettingFragment;

	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_setting);
		initView();

	}

	private void initView() {

		mFragmentManager = getFragmentManager();
		mSettingFragment = new TimeSettingFragment();
		mFragmentManager.beginTransaction()
				.add(R.id.main_container, mSettingFragment).commit();

	}

}
