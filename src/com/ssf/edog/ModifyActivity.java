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

	private ImageView mFinishBtn;// 退出程序按钮

	private String mOldPwd;// 旧密码
	private String mNewPwd;// 新密码

	private AlertDialog mAlertDialog;

	private SharedPreferenceUtil mPreferenceUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);
		mPreferenceUtil = new SharedPreferenceUtil(this);
		initView();
	}

	/**
	 * 初始化UI组件
	 */
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

	/**
	 * 验证用户输入的旧密码是否正确
	 * 
	 * @param pwd
	 * @return
	 */
	private boolean verifyOldPassword(String pwd) {

		if (pwd != null && pwd.equals(mPreferenceUtil.getPassword())) {
			return true;
		}

		mOldPwdEdt.setError(getString(R.string.password_is_error));
		mOldPwdEdt.requestFocus();

		return false;
	}

	/**
	 * 验证用户输入的新密码是否满足指定条件
	 * 
	 * @param newPwd
	 * @return
	 */
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
