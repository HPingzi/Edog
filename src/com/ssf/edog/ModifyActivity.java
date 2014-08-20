package com.ssf.edog;

import com.ssf.edog.util.SharedPreferenceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ModifyActivity extends Activity implements OnClickListener {

	private EditText mOldPwdEdt;
	private EditText mNewPwdEdt;
	private Button mSaveButton;

	private ImageView mFinishBtn;// ÍË³ö³ÌÐò°´Å¥

	private String mOldPwd;
	private String mNewPwd;

	private AlertDialog mAlertDialog;

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

		mSaveButton = (Button) findViewById(R.id.save_setting);
		mSaveButton.setOnClickListener(this);

		mFinishBtn = (ImageView) findViewById(R.id.finish);
		mFinishBtn.setOnClickListener(this);

		mAlertDialog = new AlertDialog.Builder(this).setNeutralButton(
				getString(R.string.confirm), null).create();
	}

	private void initData() {
		mOldPwd = mOldPwdEdt.getText().toString().trim();
		mNewPwd = mNewPwdEdt.getText().toString().trim();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.save_setting: {
			initData();

			if (verifyOldPassword(mOldPwd) && verifyNewPassword(mNewPwd)) {
				mPreferenceUtil.savePassword(mNewPwd);
				mAlertDialog
						.setMessage(getString(R.string.modify_password_success));
				mAlertDialog.show();
			}
		}
			break;

		case R.id.finish:

			finish();

			break;
		default:
			break;
		}

	}

	private boolean verifyOldPassword(String pwd) {

		if (pwd != null && pwd.equals(mPreferenceUtil.getPassword())) {
			return true;
		}

		mOldPwdEdt.setError(getString(R.string.password_is_error));
		mOldPwdEdt.requestFocus();

		return false;
	}

	private boolean verifyNewPassword(String newPwd) {

		if (newPwd == null || "".equals(newPwd)) {

			mNewPwdEdt.setError(getString(R.string.password_can_not_null));
			mNewPwdEdt.requestFocus();

			return false;

		} else if (newPwd.length() < 4) {

			mNewPwdEdt
					.setError(getString(R.string.password_must_than_length_than_four_big));
			mNewPwdEdt.requestFocus();
			return false;

		}

		return true;

	}
}
