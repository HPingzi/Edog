package com.ssf.edog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssf.edog.util.SharedPreferenceUtil;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView mFinishBtn;// 退出程序按钮
	private ImageView mClearBtn;// 用于清空密码输入框内容的按钮

	private Button mEnterSetting;// 用户设置按钮
	private EditText mPwdText;// 用户密码框
	private TextView mErrorDisplay;// 显示错误信息

	private SharedPreferenceUtil mPreferenceUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mPreferenceUtil = new SharedPreferenceUtil(this);
		initView();
	}

	/**
	 * 初始化UI组件
	 */
	public void initView() {

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		mClearBtn = (ImageView) findViewById(R.id.clear);
		mClearBtn.setOnClickListener(this);

		mEnterSetting = (Button) findViewById(R.id.enter_setting);
		mEnterSetting.setOnClickListener(this);

		mErrorDisplay = (TextView) findViewById(R.id.error_display);
		mPwdText = (EditText) findViewById(R.id.password_text);
	}

	/**
	 * 验证用户密码，如果密码正确则进入到菜单页面，否则显示错误信息
	 */
	public void verifyPassword() {

		String pwd = mPwdText.getText().toString().trim();

		if (pwd.equals(mPreferenceUtil.getPassword())) {

			clear();

			Intent intent = new Intent(this, MainMenuActivity.class);
			startActivity(intent);
			finish();

		} else {

			mErrorDisplay.setText(getResources().getString(
					R.string.error_display));
		}

	}

	/**
	 * 清除显示的错误信息
	 */
	public void clear() {
		mErrorDisplay.setText(null);
		mPwdText.setText(null);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.finish:
			finish();
			break;
		case R.id.clear:
			clear();
			break;
		case R.id.enter_setting:
			verifyPassword();
			break;

		default:
			break;
		}

	}
}
