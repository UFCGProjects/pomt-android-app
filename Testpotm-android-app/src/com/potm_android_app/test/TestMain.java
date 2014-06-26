package com.potm_android_app.test;

import com.potm_android_app.R;
import com.potm_android_app.screen.auth.AuthActivity;
import com.potm_android_app.screen.main.MainActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class TestMain extends ActivityInstrumentationTestCase2<AuthActivity> {

	private Solo mSolo;

	public TestMain() {
		super(AuthActivity.class);
	}

	public void setUp() throws Exception {
		mSolo = new Solo(getInstrumentation(), getActivity());
	}

	public void testLogin() throws Exception {
		View buttonSignIn = mSolo.getView(R.id.buttonContinuar);
		
	
		mSolo.clickOnView(buttonSignIn);
		
	///	mSolo.assertCurrentActivity("OK",MainActivity.class);


	}

	@Override
	public void tearDown() throws Exception {
		mSolo.finishOpenedActivities();
	}
}