package com.potm_android_app.model;

public class Ti {

    private String mHour;
	private String mMinutes;
    private String mActivity;

    public Ti(String activity, String hour,String minutes) {
        mActivity = activity;
        mHour = hour;
		mMinutes = minutes;
    }

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    public String getMinutes() {
		return mMinutes;
	}

	public void setMinutes(String mMinutes) {
		this.mMinutes = mMinutes;
	}

	public String getActivity() {
        return mActivity;
    }

    public void setActivity(String activity) {
        mActivity = activity;
    }
}
