package com.potm_android_app.test;

import com.potm_android_app.screen.main.MainActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class TestMain extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public TestMain() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testPreferenceIsSaved() throws Exception {

	}
	
	

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}