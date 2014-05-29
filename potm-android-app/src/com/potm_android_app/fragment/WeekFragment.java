/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG
 * All rights reserved.
 */
package com.potm_android_app.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.potm_android_app.R;
import com.potm_android_app.adapter.TiAdapter;
import com.potm_android_app.model.Ti;

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
            //            ((ListView) view).setEmptyView(rootView
            //                    .findViewById(android.R.id.empty));
        }

        //        ArrayList<Ti> list = new ArrayList<Ti>();
        //
        //        for (int i = 0; i < 5; i++) {
        //            list.add(new Ti("Teste " + i, String.valueOf(i * 5)));
        //        }
        //
        //        for (int i = 0; i < 3; i++) {
        //            refreshUI(list);
        //        }

        return rootView;
    }

    /**
     * Update players.
     *
     * @param players the players
     */
    public void updateTi(List<Ti> tis) {

        //        Log.d("POMT_DEBUG", "test " + mListTi.size());

        if ((mListTi != null) && (mTiAdapter != null)) {
            mListTi.clear();

            mListTi.addAll(tis);

            Log.d("POMT_DEBUG", "update: " + mListTi.size());

            mTiAdapter.notifyDataSetChanged();
        }
    }

    public void refreshUI(List<Ti> tis) {
        updateTi(tis);
    }
}
