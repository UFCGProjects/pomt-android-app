package com.potm_android_app.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.potm_android_app.R;
import com.potm_android_app.model.Ti;

public class TiAdapter extends ArrayAdapter<Ti> {

    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList<Ti> mList;

    public TiAdapter(Context context, int layoutResourceId, ArrayList<Ti> list) {
        super(context, layoutResourceId, list);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        TiHolder holder = null;

        if (row == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new TiHolder();

            holder.txtTitle = (TextView) row.findViewById(R.id.textView1);
            holder.txtHours = (TextView) row.findViewById(R.id.textView2);

            row.setTag(holder);
        } else {
            holder = (TiHolder) row.getTag();
        }

        Ti ti = mList.get(position);

        holder.txtTitle.setText(ti.getTitle());
        holder.txtHours.setText(ti.getHour());

        return row;

    }

    /**
     * The Class PlayerHolder.
     */
    static class TiHolder {

        TextView txtTitle;

        TextView txtHours;

    }
}