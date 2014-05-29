package com.potm_android_app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
            PotmUtils.showToast(this, "Adding ti!");
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

        //        ArrayList<Ti> list = new ArrayList<Ti>();
        //
        //        for (int i = 0; i < 5; i++) {
        //            list.add(new Ti("Teste " + i, String.valueOf(i * 5)));
        //        }
        //
        //        for (int i = 0; i < 3; i++) {
        //            ((WeekFragment) mTabsAdapter.getRegisteredFragment(i))
        //                    .refreshUI(list);
        //        }

    }
}