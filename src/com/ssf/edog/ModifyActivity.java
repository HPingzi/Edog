package com.ssf.edog;

import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyActivity extends Activity implements OnClickListener {

	private EditText mOldPwdEdt;
	private EditText mNewPwdEdt;
	private EditText mReNewPwdEdt;
	private Button mSaveButton;

	private String mOldPwd;
	private String mNewPwd;
	private String mReNewPwd;

	private SharedPreferenceUtil mPreferenceUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);
		mPreferenceUtil = new SharedPreferenceUtil(this);
		initView();
	}

	private void initView() {

		mOldPwdEdt = (EditText) findViewById(R.id.old_pwd);
		mNewPwdEdt = (EditText) findViewById(R.id.new_pwd);
		mReNewPwdEdt = (EditText) findViewById(R.id.re_new_pwd);

		mSaveButton = (Button) findViewById(R.id.save_setting);
		mSaveButton.setOnClickListener(this);
	}

	private void initData() {
		mOldPwd = mOldPwdEdt.getText().toString().trim();
		mNewPwd = mNewPwdEdt.getText().toString().trim();
		mReNewPwd = mReNewPwdEdt.getText().toString().trim();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.save_setting: {
			initData();

			if (verifyOldPassword(mOldPwd)
					&& verifyNewPassword(mNewPwd, mReNewPwd)) {
				mPreferenceUtil.savePassword(mNewPwd);
			}
		}
			break;

		default:
			break;
		}

	}

	private boolean verifyOldPassword(String pwd) {

		if (pwd != null && pwd.equals(mPreferenceUtil.getPassword())) {
			return true;
		}
		return false;
	}

	private boolean verifyNewPassword(String newPwd, String reNewPwd) {

		if (newPwd == null || "".equals(newPwd)) {

		} else if (newPwd.length() < 4) {

		}

		if (reNewPwd == null || "".equals(reNewPwd)) {
			return false;
		} else if (reNewPwd.length() < 4) {
			return false;
		}

		if (newPwd.equals(reNewPwd)) {
			return true;
		}

		return false;

	}
}
