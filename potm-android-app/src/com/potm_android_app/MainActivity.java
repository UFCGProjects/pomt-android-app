package com.potm_android_app;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.potm_android_app.adapter.TabsPagerAdapter;
import com.potm_android_app.utils.PotmUtils;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	TabsPagerAdapter mTabsAdapter;

	ViewPager mViewPager;

	private String[] mTabsNames = { "Semana 3", "Semana 2", "Semana 1" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		final ActionBar actionBar = getActionBar();

		actionBar.setHomeButtonEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mTabsAdapter);

		// Adding Tabs

		for (String tab_name : mTabsNames) {
			getActionBar().addTab(
					getActionBar().newTab().setText(tab_name)
							.setTabListener(this));
		}

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {

						actionBar.setSelectedNavigationItem(position);
					}
				});

	}

	@Override
	protected void onResume() {
		super.onResume();

		requestTis();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_add_ti:
			if (isConnected()) {
				Toast.makeText(getBaseContext(), "Você está conectado",
						Toast.LENGTH_LONG).show();
				new JSONParse().execute();
			} else {
				Toast.makeText(getBaseContext(), "Você não está conectado!",
						Toast.LENGTH_LONG).show();
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {

		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	private void requestTis() {

	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected JSONObject doInBackground(String... args) {

			JSONParser jParser = new JSONParser();

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("username", "developer"));
			nameValuePairs.add(new BasicNameValuePair("date_end", String
					.valueOf(new GregorianCalendar().getTimeInMillis())));
			nameValuePairs.add(new BasicNameValuePair("date_begin", String
					.valueOf(new GregorianCalendar().getTimeInMillis())));
			nameValuePairs.add(new BasicNameValuePair("category", "nenhuma"));
			nameValuePairs.add(new BasicNameValuePair("description", "nada"));
			nameValuePairs.add(new BasicNameValuePair("title", "les"));

			JSONObject json = jParser.postData(PotmUtils.getServerURL(),
					nameValuePairs);

			if (json == null) {
				Log.d("POMT", "error!!!!");
			}

			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			Toast.makeText(getBaseContext(), "enviou os dados",
					Toast.LENGTH_LONG).show();

		}

	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}
}