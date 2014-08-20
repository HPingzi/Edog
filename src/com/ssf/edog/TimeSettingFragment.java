package com.ssf.edog;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.ssf.edog.config.Config;
import com.ssf.edog.util.MachineUtil;
import com.ssf.edog.util.SharedPreferenceUtil;
import com.ssf.edog.util.TimeUtils;

@SuppressLint("NewApi")
public class TimeSettingFragment extends Fragment implements OnClickListener,
		OnItemSelectedListener, MachineUtil.OnStateListener {

	private Spinner mSpinner;

	private TextView mOnTimeBtn;
	private TextView mOnTimeLable;

	private TextView mOffTimeBtn;
	private TextView mOffTimeLabel;

	private View mOnTimeContainer;
	private View mOffTimeContainer;

	private TimePickerDialog mPickOnTimeDialog;
	private TimePickerDialog mPickOffTimeDialog;

	private Button mSaveSettingBtn;

	private TextView mAlertTextView;

	private SharedPreferenceUtil mPreferenceUtil;

	private AlarmManager mAlarmManager;

	private AlertDialog mAlertDialog;

	MachineUtil mMachineUtil;

	/**
	 * 开机时间
	 */
	private int mOnHour = 0;
	private int mOnMinute = 0;

	/**
	 * 关机时间
	 */
	private int mOffHour = 0;
	private int mOffMinute = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity() != null) {
			mPreferenceUtil = new SharedPreferenceUtil(getActivity());
			mAlarmManager = (AlarmManager) getActivity().getSystemService(
					Context.ALARM_SERVICE);
			mMachineUtil = new MachineUtil();
			mMachineUtil.setOnStateListener(this);
		}
	}

	/**
	 * 初始化UI组件
	 */
	public void initView() {
		mSpinner = (Spinner) getView().findViewById(R.id.spinner);
		mSpinner.setSelection(0);
		mSpinner.setOnItemSelectedListener(this);

		mOnTimeContainer = getView().findViewById(R.id.on_time_container);
		mOffTimeContainer = getView().findViewById(R.id.off_time_container);

		mOnTimeLable = (TextView) getView().findViewById(R.id.on_time_lable);
		mOffTimeLabel = (TextView) getView().findViewById(R.id.off_time_lable);

		mOnTimeBtn = (TextView) getView().findViewById(R.id.on_time);
		mOnTimeBtn.setOnClickListener(this);

		mOffTimeBtn = (TextView) getView().findViewById(R.id.off_time);
		mOffTimeBtn.setOnClickListener(this);

		mSaveSettingBtn = (Button) getView().findViewById(R.id.save_setting);
		mSaveSettingBtn.setOnClickListener(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setNeutralButton(getString(R.string.confirm), null);
		mAlertDialog = builder.create();

		mAlertTextView = (TextView) getView().findViewById(R.id.alert);

		initDisplayTime(mPreferenceUtil.getType());

		mPickOnTimeDialog = new TimePickerDialog(getActivity(),
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						mOnHour = hourOfDay;
						mOnMinute = minute;

						mOnTimeBtn.setText(mOnHour + " : " + mOnMinute);

					}
				}, mOnHour, mOnMinute, true);

		mPickOffTimeDialog = new TimePickerDialog(getActivity(),
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						mOffHour = hourOfDay;
						mOffMinute = minute;

						mOffTimeBtn.setText(mOffHour + " : " + mOffMinute);

					}
				}, mOffHour, mOffMinute, true);

		mSpinner.setSelection(mPreferenceUtil.getType());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.time_setting_fragment_layout,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.save_setting:

			saveSetting();

			break;
		case R.id.on_time:

			mPickOnTimeDialog.setCancelable(true);
			mPickOnTimeDialog.show();

			break;
		case R.id.off_time:

			mPickOffTimeDialog.setCancelable(true);
			mPickOffTimeDialog.show();

			break;
		default:
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			closeTimingOption();
			break;
		case 1:
			switchTimingOnAndOff();
			break;
		case 2:
			switchTimingReboot();
			break;
		case 3:
			switchTimingOff();
			break;
		}

		initDisplayTime(position);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void initDisplayTime(int position) {
		switch (position) {

		case SharedPreferenceUtil.AUTO_OFF:

			mOffHour = mPreferenceUtil.getOffHour();
			mOffMinute = mPreferenceUtil.getOffMinute();

			mOffTimeBtn.setText(mOffHour + " : " + mOffMinute);

			break;
		case SharedPreferenceUtil.AUTO_REBOOT:

			mOnHour = mPreferenceUtil.getRebootHour();
			mOnMinute = mPreferenceUtil.getRebootMinute();

			mOnTimeBtn.setText(mOnHour + " : " + mOnMinute);

			break;
		case SharedPreferenceUtil.AUTO_ON_OFF:

			mOnHour = mPreferenceUtil.getOnHour();
			mOnMinute = mPreferenceUtil.getOnMinute();

			mOffHour = mPreferenceUtil.getOffHour();
			mOffMinute = mPreferenceUtil.getOffMinute();

			mOnTimeBtn.setText(mOnHour + " : " + mOnMinute);
			mOffTimeBtn.setText(mOffHour + " : " + mOffMinute);

			break;

		default:
			break;
		}
	}

	private void saveSetting() {

		Intent intent = new Intent(Config.SWITCH_ACTION);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mAlarmManager.cancel(pendingIntent);
		mMachineUtil.close();

		switch (mSpinner.getSelectedItemPosition()) {

		case 0: {

			mPreferenceUtil.setType(SharedPreferenceUtil.CLOSE_SETTING);
			mAlertDialog.setTitle(getResources().getString(
					R.string.info_prompt_title));
			mAlertDialog.setMessage(getResources().getString(
					R.string.close_timing_option));
			mAlertDialog.show();
		}
			break;

		case 1: {

			mPreferenceUtil.setOnHour(mOnHour);
			mPreferenceUtil.setOnMinute(mOnMinute);

			mPreferenceUtil.setOffHour(mOffHour);
			mPreferenceUtil.setOffMinute(mOffMinute);

			mPreferenceUtil.setType(SharedPreferenceUtil.AUTO_ON_OFF);

			mMachineUtil.setBonh((byte) mOnHour);
			mMachineUtil.setBonm((byte) mOnMinute);

			mMachineUtil.setBoffh((byte) mOffHour);
			mMachineUtil.setBoffm((byte) mOffMinute);

			mMachineUtil.openMachine();

		}
			break;
		case 2: {

			mPreferenceUtil.setRebootHour(mOnHour);
			mPreferenceUtil.setRebootMinute(mOnMinute);

			mPreferenceUtil.setType(SharedPreferenceUtil.AUTO_REBOOT);

			mAlarmManager.set(AlarmManager.RTC_WAKEUP,
					TimeUtils.calculateRebootTime(mOnHour, mOnMinute),
					pendingIntent);
			mAlertDialog.setTitle(getResources().getString(
					R.string.info_prompt_title));
			mAlertDialog.setMessage(getResources().getString(
					R.string.timing_reboot_msg));
			mAlertDialog.show();

		}
			break;
		case 3: {

			mPreferenceUtil.setOffHour(mOffHour);
			mPreferenceUtil.setOffMinute(mOffMinute);

			mPreferenceUtil.setType(SharedPreferenceUtil.AUTO_OFF);

			mAlarmManager.set(AlarmManager.RTC_WAKEUP,
					TimeUtils.calculateRebootTime(mOffHour, mOffMinute),
					pendingIntent);
			mAlertDialog.setTitle(getResources().getString(
					R.string.info_prompt_title));
			mAlertDialog.setMessage(getResources().getString(
					R.string.timing_on_off_msg));
			mAlertDialog.show();

		}
			break;

		}

	}

	private void switchTimingOff() {

		mOnTimeContainer.setVisibility(View.GONE);
		mOnTimeLable.setVisibility(View.GONE);

		mOffTimeContainer.setVisibility(View.VISIBLE);
		mOffTimeLabel.setVisibility(View.VISIBLE);

		mAlertTextView.setVisibility(View.INVISIBLE);

	}

	private void switchTimingOnAndOff() {

		mOnTimeContainer.setVisibility(View.VISIBLE);
		mOnTimeLable.setVisibility(View.VISIBLE);
		mOnTimeLable.setText(R.string.on_time);

		mOffTimeContainer.setVisibility(View.VISIBLE);
		mOffTimeLabel.setVisibility(View.VISIBLE);

		mAlertTextView.setVisibility(View.VISIBLE);

	}

	private void switchTimingReboot() {

		mOnTimeContainer.setVisibility(View.VISIBLE);
		mOnTimeLable.setVisibility(View.VISIBLE);
		mOnTimeLable.setText(R.string.timing_reboot);

		mOffTimeContainer.setVisibility(View.GONE);
		mOffTimeLabel.setVisibility(View.GONE);

		mAlertTextView.setVisibility(View.INVISIBLE);
	}

	private void closeTimingOption() {

		mOnTimeContainer.setVisibility(View.GONE);
		mOnTimeLable.setVisibility(View.GONE);

		mOffTimeContainer.setVisibility(View.GONE);
		mOffTimeLabel.setVisibility(View.GONE);

		mAlertTextView.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onError(String msg) {

		mAlertDialog.setTitle(getResources().getString(
				R.string.error_prompt_title));
		mAlertDialog.setMessage(msg);
		mAlertDialog.show();

	}

	@Override
	public void onSccessful(String msg) {

		mAlertDialog.setTitle(getResources().getString(
				R.string.info_prompt_title));
		mAlertDialog.setMessage(msg);
		mAlertDialog.show();

	}

}
