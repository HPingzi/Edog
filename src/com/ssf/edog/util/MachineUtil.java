package com.ssf.edog.util;

import java.util.Calendar;

import com.ssf.log.PjLog;
import com.xboot.stdcall.posix;

public class MachineUtil {

	private final int _TIME = 180000;

	private OnStateListener mOnStateListener;

	public void setOnStateListener(OnStateListener mOnStateListener) {
		this.mOnStateListener = mOnStateListener;
	}

	/**
     */
	public interface OnStateListener {
		void onError(String msg);

		void onSccessful(String msg);
	}

	private byte bonh = -1, bonm = -1, boffh = -1, boffm = -1;

	public void setBonh(byte bonh) {
		this.bonh = bonh;
	}

	public void setBonm(byte bonm) {
		this.bonm = bonm;
	}

	public void setBoffh(byte boffh) {
		this.boffh = boffh;
	}

	public void setBoffm(byte boffm) {
		this.boffm = boffm;
	}

	public void openMachine() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		long nowTime = c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, boffh < 0 ? hour : boffh);
		c.set(Calendar.MINUTE, boffm < 0 ? minute : boffm);
		long offTime = c.getTimeInMillis();
		if ((offTime - nowTime) < _TIME && (offTime - nowTime) > 0) {
			if (mOnStateListener != null) {
				mOnStateListener.onError("关机时间不能小于3分钟");
			}
			return;
		}
		c.set(Calendar.HOUR_OF_DAY, bonh < 0 ? hour : bonh);
		c.set(Calendar.MINUTE, bonm < 0 ? minute : bonm);
		long onTime = c.getTimeInMillis();
		if ((onTime - offTime) == 0) {
			if (mOnStateListener != null) {
				mOnStateListener.onError("开机时间和关机时间不能相等");
			}
			return;
		}
		if ((onTime - offTime) < _TIME && (onTime - offTime) > 0) {
			if (mOnStateListener != null) {
				mOnStateListener.onError("开机时间比关机时间必须大于3分钟");
			}
			return;
		}
		int[] off = compareDate(offTime, nowTime);
		int[] on = compareDate(onTime, offTime);
		VerifyAndSet(off, on);
	}

	public void close() {
		setPowerOnOff(bonh, bonm, boffh, boffm, (byte) 0);
	}

	public int[] compareDate(long time1, long time2) {
		int[] ls = new int[2];
		long diff = time1 - time2;
		// 如果小于0 代表时间已经过去，所以加一天的时间
		if (diff < 0) {
			diff = diff + (1000 * 60 * 60 * 24);
		}
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60))
				/ (1000 * 60);
		long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
		ls[0] = (int) hours;
		ls[1] = (int) minutes;
		PjLog.d("SSF", " ++++ " + days + " days " + hours + " hours " + minutes
				+ " minutes " + seconds + "seconds" + "\n");
		return ls;
	}

	private void VerifyAndSet(int[] off, int[] on) {
		int on_h = on[0];
		int on_m = on[1];
		int off_h = off[0];
		int off_m = off[1];
		setPowerOnOff((byte) on_h, (byte) on_m, (byte) off_h, (byte) off_m,
				(byte) 3);
	}

	// 设置开关机
	private int setPowerOnOff(byte on_h, byte on_m, byte off_h, byte off_m,
			byte enable) {
		int fd, ret;
		fd = posix.open("/dev/McuCom", posix.O_RDWR, 0666);
		if (fd < 0) {
			if (3 == enable) {
				if (mOnStateListener != null) {
					// mOnStateListener.onError("开启定时开关机-打开节点失败");
				}
			} else if (0 == enable) {
				if (mOnStateListener != null) {
					// mOnStateListener.onError("取消定时开关机-打开节点失败");
				}
			}
		} else {
			ret = posix.poweronoff(on_h, on_m, off_h, off_m, enable, fd);
			if (ret != 0) {
				if (3 == enable) {
					if (mOnStateListener != null) {
						// mOnStateListener.onError("开启定时开关机-设置命令失败！");
					}
				} else if (0 == enable) {
					if (mOnStateListener != null) {
						// mOnStateListener.onError("关闭定时开关机-设置命令失败！");
					}
				}
			} else {
				if (3 == enable) {
					PjLog.d("SSF", "定时开关机设置成功：距离关机时间还有" + off_h + ":" + off_m);
					if (mOnStateListener != null) {
						mOnStateListener.onSccessful("定时开关机设置成功");
					}
				} else if (0 == enable) {
					PjLog.d("SSF", "定时开关机：关");
					if (mOnStateListener != null) {
						// mOnStateListener.onSccessful("定时开关机：关");
					}
				}
			}
		}
		posix.close(fd);
		return 0;
	}

	public String getCurTime() {
		String time = "";
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		time = String.valueOf(year) + "-" + String.valueOf(month) + "-"
				+ String.valueOf(day) + " " + String.valueOf(hour) + ":"
				+ String.valueOf(minute) + ":" + String.valueOf(second);

		return time;
	}

}
