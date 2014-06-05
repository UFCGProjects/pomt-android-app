/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG
 * All rights reserved.
 */
package com.potm_android_app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.potm_android_app.R;
import com.potm_android_app.adapter.TiAdapter;
import com.potm_android_app.model.Ti;
import com.potm_android_app.utils.MyLog;
import com.potm_android_app.utils.PotmUtils;

/**
 * The Class TableFragment.
 */
public class WeekFragment extends Fragment {

    /**
     * The m list players.
     */
    private ArrayList<Ti> mListTi;

    /**
     * The m players adapter.
     */
    private TiAdapter mTiAdapter;

    private LinearLayout mLayoutTitle;

    private TextView mTotalHours;

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_week, container,
                false);

        mListTi = new ArrayList<Ti>();

        mTiAdapter = new TiAdapter(getActivity(), R.layout.adapter_ti, mListTi);

        View view = rootView.findViewById(android.R.id.list);

        if (view instanceof ListView) {
            ((ListView) view).setAdapter(mTiAdapter);
            ((ListView) view).setEmptyView(rootView
                    .findViewById(android.R.id.empty));
        }

        mLayoutTitle = (LinearLayout) rootView.findViewById(R.id.layoutTitle);

        //                    .setVisibility(View.VISIBLE);
        mTotalHours = (TextView) rootView.findViewById(R.id.textViewTotal);
        //                    .setText(String.valueOf(total));

        return rootView;
    }

    /**
     * Update players.
     *
     * @param players the players
     */
    public void updateTi(List<Ti> tis) {
        if ((mListTi != null) && (mTiAdapter != null)) {
            mListTi.clear();

            mListTi.addAll(tis);

            Collections.sort(mListTi);
            //            Collections.reverse(mListTi);

            mTiAdapter.notifyDataSetChanged();
        }
    }

    public void refreshUI(List<Ti> tis, double total) {
        MyLog.debug("Updating UI...");

        updateTi(tis);

        if ((mLayoutTitle != null) && (mTotalHours != null)) {
            if (total > 0) {
                mLayoutTitle.setVisibility(View.VISIBLE);
                mTotalHours.setText(PotmUtils.formatDouble(total) + " hours");
            } else {
                mLayoutTitle.setVisibility(View.GONE);
            }
        }
    }
}
