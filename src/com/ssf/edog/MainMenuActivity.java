package com.ssf.edog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenuActivity extends BaseActivity implements OnClickListener {

	private Button mPowerBtn;// 自动开关机设置按钮
	private Button mEdogBtn;// 电子狗设置按钮
	private Button mPwdSettingBtn;// 密码设置按钮

	private ImageView mFinishBtn;// 退出程序按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		initView();
	}

	/**
	 * 初始化UI组件
	 */
	public void initView() {
		mPowerBtn = (Button) findViewById(R.id.on_off_btn);
		mPowerBtn.setOnClickListener(this);

		mEdogBtn = (Button) findViewById(R.id.edog_seting_btn);
		mEdogBtn.setOnClickListener(this);

		mPwdSettingBtn = (Button) findViewById(R.id.modify_pwd_setting_btn);
		mPwdSettingBtn.setOnClickListener(this);

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.on_off_btn:
			openActivity(TimeSettingActivity.class);// 进入定时开关机设置
			break;
		case R.id.edog_seting_btn:
			openActivity(SettingActivity.class);// 进入电子狗设置
			break;
		case R.id.modify_pwd_setting_btn:
			openActivity(ModifyActivity.class);// 进入密码设置
			break;
		case R.id.finish:
			finish();
			break;
		default:
			break;
		}

	}
}
