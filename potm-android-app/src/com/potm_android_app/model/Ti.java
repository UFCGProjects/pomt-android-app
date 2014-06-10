package com.potm_android_app.model;

import org.joda.time.Interval;

import com.potm_android_app.utils.PotmUtils;

public class Ti {

    private String mTitle;
    private Interval mInterval;
    private String mDescription;
    private String mCategory;
    private String mProportion;

    public Ti(String title, Interval interval, String category,
            String description) {
        mTitle = title;
        mInterval = interval;
        mDescription = "";
        mCategory = "";
        mProportion = "";
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Interval getInterval() {
        return mInterval;
    }

    public void setInterval(Interval interval) {
        mInterval = interval;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getHoursBegin() {
        return mInterval.getStart().toString(PotmUtils.getDateTimeFormat());
    }

    public CharSequence getProportion() {
        return mProportion;
    }

    public CharSequence getHoursEnd() {
        return mInterval.getEnd().toString(PotmUtils.getDateTimeFormat());
    }

}