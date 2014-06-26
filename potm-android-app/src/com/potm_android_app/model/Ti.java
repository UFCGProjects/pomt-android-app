package com.potm_android_app.model;

import org.joda.time.Interval;

import com.potm_android_app.utils.PotmUtils;

public class Ti implements Comparable<Ti> {

	private String mTitle;
	private Interval mInterval;
	private String mDescription;
	private String mCategory;
	private final double mProportion;
	private int mPriority;

	public Ti(String title, Interval interval, String category,
			String description) {
		this.mTitle = title;
		this.mInterval = interval;
		this.mDescription = "";
		this.mCategory = "";
		this.mProportion = 0;
		this.mPriority = 1;
	}

	public Ti(String title, double proportion, int priority) {
		this.mTitle = title;
		this.mProportion = proportion;
		this.mPriority = priority;
	}

	public String getTitle() {
		return this.mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public Interval getInterval() {
		return this.mInterval;
	}

	public void setInterval(Interval interval) {
		this.mInterval = interval;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public String getCategory() {
		return this.mCategory;
	}

	public void setCategory(String category) {
		this.mCategory = category;
	}

	public String getHoursBegin() {
		return this.mInterval.getStart()
				.toString(PotmUtils.getDateTimeFormat());
	}

	public double getProportion() {
		return this.mProportion;
	}

	public String getHoursEnd() {
		return this.mInterval.getEnd().toString(PotmUtils.getDateTimeFormat());
	}

	@Override
	public int compareTo(Ti another) {
		if ((Double.compare(this.getProportion(), another.getProportion()) == 0) == (this
				.getProportion() == another.getProportion())) {
			return 0;
		} else {
			return Double
					.compare(this.getProportion(), another.getProportion());
		}
	}

	public int getPriority() {
		return this.mPriority;
	}

	public void setPriority(int priority) {
		this.mPriority = priority;
	}
}
