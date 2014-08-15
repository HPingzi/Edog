package com.ssf.edog;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class TimeSettingFragment extends Fragment implements OnClickListener,
		OnItemSelectedListener {
	private Spinner mSpinner;
	private TextView mOnTimeBtn;
	private TextView mOffTimeBtn;
	private TextView mOnTimeLable;
	private TextView mOffTimeLabel;
	private View mOnTimeContainer;
	private View mOffTimeContainer;
	private TimePickerDialog mPickDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

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

		mPickDialog = new TimePickerDialog(getActivity(), null, 5, 5, true);

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
		mPickDialog.show();
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

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

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
