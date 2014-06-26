
package com.potm_android_app.model;

import java.util.Comparator;

import org.joda.time.Interval;

import com.potm_android_app.utils.PotmUtils;

public class Ti implements Comparable<Ti> {

    private String mTitle;
    private Interval mInterval;
    private String mDescription;
    private String mCategory;
    private double mProportion;
    private int mPriority;

    public Ti(String title, Interval interval, String category,
            String description) {
        mTitle = title;
        mInterval = interval;
        mDescription = "";
        mCategory = "";
        mProportion = 0;
        mPriority = 1;
    }

    public Ti(String title, double proportion, int priority) {
        mTitle = title;
        mProportion = proportion;
        mPriority = priority;
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

    public double getProportion() {
        return mProportion;
    }

    public String getHoursEnd() {
        return mInterval.getEnd().toString(PotmUtils.getDateTimeFormat());
    }

    @Override
    public int compareTo(Ti another) {
        if (getProportion() < another.getProportion()) {
            return 1;
        }
        if (getProportion() > another.getProportion()) {
            return -1;
        }

        return 0;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

}
