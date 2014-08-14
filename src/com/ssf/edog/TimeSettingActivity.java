package com.ssf.edog;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class TimeSettingActivity extends Activity implements SwitchFragment {

	private TimeBootSettingFragment mBootFragement;
	private TimeSettingFragment mSettingFragment;
	private TimeSettingFragment mOffFragment;
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_setting);

		initView();

	}

	private void initView() {

		mFragmentManager = getFragmentManager();

		mBootFragement = new TimeBootSettingFragment();
		mSettingFragment = new TimeSettingFragment();
		mOffFragment = new TimeSettingFragment();

		mFragmentManager.beginTransaction()
				.add(R.id.main_container, mSettingFragment).commit();

	}

	@Override
	public void switchToNextFragmet(int position) {
		switch (position) {
		case 0:

			mFragmentManager.beginTransaction()
					.replace(R.id.main_container, mSettingFragment).commit();
			break;
		case 1:
			mFragmentManager.beginTransaction()
					.replace(R.id.main_container, mBootFragement).commit();
			break;
		case 2:

			mFragmentManager.beginTransaction()
					.replace(R.id.main_container, mOffFragment).commit();
			break;

		default:
			break;
		}
	}
}
