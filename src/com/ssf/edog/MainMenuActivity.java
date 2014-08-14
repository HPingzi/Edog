package com.ssf.edog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends BaseActivity implements OnClickListener {

	private Button mPowerBtn;
	private Button mEdogBtn;
	private Button mPwdSettingBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		initView();
	}

	public void initView() {
		mPowerBtn = (Button) findViewById(R.id.on_off_btn);
		mPowerBtn.setOnClickListener(this);

		mEdogBtn = (Button) findViewById(R.id.edog_seting_btn);
		mEdogBtn.setOnClickListener(this);

		mPwdSettingBtn = (Button) findViewById(R.id.modify_pwd_setting_btn);
		mPwdSettingBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.on_off_btn:
			openActivity(TimeSettingActivity.class);
			break;
		case R.id.edog_seting_btn:
			openActivity(SettingActivity.class);
			break;
		case R.id.modify_pwd_setting_btn:
			openActivity(ModifyActivity.class);
			break;

		default:
			break;
		}

	}
}
