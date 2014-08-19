package com.ssf.edog;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
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
		OnItemSelectedListener {

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

	private SharedPreferenceUtil mPreferenceUtil;

	private AlarmManager mAlarmManager;

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

		initDisplayTime();

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

			mPickOnTimeDialog.setCancelable(false);
			mPickOnTimeDialog.show();

			break;
		case R.id.off_time:

			mPickOffTimeDialog.setCancelable(false);
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
			switchTimingOnAndOff();
			break;
		case 1:
			switchTimingReboot();
			break;
		case 2:
			switchTimingOff();
			break;
		}

		initDisplayTime();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void initDisplayTime() {
		switch (mPreferenceUtil.getType()) {

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
			mOffTimeBtn.setText(mOffHour + " : " + mOffMinute);
			mOnTimeBtn.setText(mOnHour + " : " + mOnMinute);
			break;

		default:
			break;
		}
	}

	private void saveSetting() {

		Intent intent = new Intent(Config.SWITCH_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		switch (mSpinner.getSelectedItemPosition()) {

		case 0: {
			mPreferenceUtil.setOnHour(mOnHour);
			mPreferenceUtil.setOnMinute(mOnMinute);
			mPreferenceUtil.setOffHour(mOffHour);
			mPreferenceUtil.setOffMinute(mOffMinute);
			mPreferenceUtil.setType(SharedPreferenceUtil.AUTO_ON_OFF);
			MachineUtil machineUtil = new MachineUtil();
			machineUtil.setBonh((byte) mOnHour);
			machineUtil.setBonm((byte) mOnMinute);
			machineUtil.setBoffh((byte) mOffHour);
			machineUtil.setBoffm((byte) mOffMinute);
			machineUtil.openMachine();

		}
			break;
		case 1: {
			mPreferenceUtil.setRebootHour(mOnHour);
			mPreferenceUtil.setRebootMinute(mOnMinute);
			mPreferenceUtil.setType(SharedPreferenceUtil.AUTO_REBOOT);
			mAlarmManager.cancel(pendingIntent);
			mAlarmManager.set(AlarmManager.RTC_WAKEUP,
					TimeUtils.calculateRebootTime(mOnHour, mOnMinute),
					pendingIntent);
		}
			break;
		case 2: {
			mPreferenceUtil.setOffHour(mOffHour);
			mPreferenceUtil.setOffMinute(mOffMinute);
			mPreferenceUtil.setType(SharedPreferenceUtil.AUTO_OFF);
			mAlarmManager.cancel(pendingIntent);
			mAlarmManager.set(AlarmManager.RTC_WAKEUP,
					TimeUtils.calculateRebootTime(mOffHour, mOffMinute),
					pendingIntent);

		}
			break;

		}

	}

	private void switchTimingOff() {

		mOnTimeContainer.setVisibility(View.GONE);
		mOnTimeLable.setVisibility(View.GONE);

		mOffTimeContainer.setVisibility(View.VISIBLE);
		mOffTimeLabel.setVisibility(View.VISIBLE);

	}

	private void switchTimingOnAndOff() {

		mOnTimeContainer.setVisibility(View.VISIBLE);
		mOnTimeLable.setVisibility(View.VISIBLE);
		mOnTimeLable.setText(R.string.on_time);

		mOffTimeContainer.setVisibility(View.VISIBLE);
		mOffTimeLabel.setVisibility(View.VISIBLE);

	}

	private void switchTimingReboot() {

		mOnTimeContainer.setVisibility(View.VISIBLE);
		mOnTimeLable.setVisibility(View.VISIBLE);
		mOnTimeLable.setText(R.string.timing_reboot);

		mOffTimeContainer.setVisibility(View.GONE);
		mOffTimeLabel.setVisibility(View.GONE);
	}

}
