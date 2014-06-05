package com.potm_android_app;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.potm_android_app.adapter.TabsPagerAdapter;
import com.potm_android_app.asynctask.DownloadJSONTask;
import com.potm_android_app.asynctask.DownloadJSONTask.DownloadJSONInterface;
import com.potm_android_app.fragment.WeekFragment;
import com.potm_android_app.model.Ti;
import com.potm_android_app.utils.MyLog;
import com.potm_android_app.utils.PotmUtils;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener, DownloadJSONInterface {

    Intent intent;
    TabsPagerAdapter mTabsAdapter;

    ViewPager mViewPager;
    RegisterDialog dialog;
    ProgressDialog progress;
	private static ArrayList<Ti> list;
	private List<String>  titles = new ArrayList<String>();
;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        progress = new ProgressDialog(this);
        
        list = new ArrayList<Ti>();
        
        mTabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        View view = findViewById(R.id.pager);

        if (view instanceof ViewPager) {
            mViewPager = (ViewPager) view;
            mViewPager.setAdapter(mTabsAdapter);
        }

        // Adding Tabs
        int week = new DateTime().getWeekOfWeekyear();

        for (int i = 0; i < 3; i++) {
            getActionBar().addTab(
                    getActionBar().newTab().setText("Semana " + (week - i))
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
                dialog = new RegisterDialog(this,titles);
                dialog.show();
            } else {
                PotmUtils.showNotConnected(this);
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

        if (mViewPager != null) {
            mViewPager.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
    }

    private void requestTis() {
    	launchRingDialog();
        if (isConnected()) {
            new DownloadJSONTask(this).execute(PotmUtils.getServerURL());
        } else {
            PotmUtils.showNotConnected(this);
        }
    }

    @Override
    public void callback(JSONObject json) {
        int week = new DateTime().getWeekOfWeekyear();

        for (int i = 0; i < 3; i++) {
            try {
                refreshFragment(mTabsAdapter.getRegisteredFragment(i),
                        json.getJSONObject(String.valueOf(week - i)));
                allTitles();
            } catch (JSONException e) {
                MyLog.error("Error when parsing json on callback", e);
            }
        }

    }

    private void refreshFragment(Fragment fragment, JSONObject json) {
        
        Ti ti;
        try {
            JSONObject jsonTis = json.getJSONObject("tis");

            Iterator<?> keys = jsonTis.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();

                if (jsonTis.get(key) instanceof JSONObject) {
                    String title = key;
                    String proportion = (jsonTis.getJSONObject(key).getDouble(
                            "proporcion") * 100)
                            + "%";

                    ti = new Ti(title, proportion);
                    list.add(ti);
                }

            }

            if (mTabsAdapter != null) {
                if (fragment instanceof WeekFragment) {
                    ((WeekFragment) fragment).refreshUI(list,
                            json.getDouble("total"));
                }
            }

        } catch (JSONException e) {
            MyLog.error("Error when add Ti", e);
        }

    }

    public void allTitles() {
    	
    	for (Ti currentTi : list) {
    		if (!titles.contains(currentTi.getTitle())) 
			titles.add(currentTi.getTitle());
		}
    	
	}
    
    public void launchRingDialog() {
    	        final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Espere um pouco ...", "Baixando Informações ...", true);
    	        ringProgressDialog.setCancelable(true);
    	        new Thread(new Runnable() {
    	            @Override
    	            public void run() {
    	                try {
    	                    Thread.sleep(10000);
    	                } catch (Exception e) {
    	 
    	                }
    	                ringProgressDialog.dismiss();
    	            }
    	        }).start();
    	    }

    
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if ((networkInfo != null) && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}