package com.potm_android_app.model;

public class Ti {

    private String mHour;
    private String mTitle;

    public Ti(String title, String hour) {
        mTitle = title;
        mHour = hour;
    }

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
