package com.potm_android_app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.potm_android_app.fragment.WeekFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);

        registeredFragments.put(0, new WeekFragment());
        registeredFragments.put(1, new WeekFragment());
        registeredFragments.put(2, new WeekFragment());
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
        case 0:
            // Table fragment activity
            return registeredFragments.get(0);
        case 1:
            // Chips fragment activity
            return registeredFragments.get(1);
        case 2:
            // Chips fragment activity
            return registeredFragments.get(2);
        }

        return null;

        //        return MyFragment.newInstance(...); 
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }


    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}