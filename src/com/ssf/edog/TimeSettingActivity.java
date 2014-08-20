package com.ssf.edog;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TimeSettingActivity extends Activity implements OnClickListener {

	private TimeSettingFragment mSettingFragment;

	private FragmentManager mFragmentManager;

	private ImageView mFinishBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_setting);
		initView();

	}

	private void initView() {

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		mFragmentManager = getFragmentManager();
		mSettingFragment = new TimeSettingFragment();
		mFragmentManager.beginTransaction()
				.add(R.id.main_container, mSettingFragment).commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.finish:
			finish();

			break;

		default:
			break;
		}

	}

}
