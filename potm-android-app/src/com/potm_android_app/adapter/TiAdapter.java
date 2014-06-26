
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
import com.potm_android_app.utils.PotmUtils;

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

            View view = row.findViewById(R.id.textViewTitle);
            if (view instanceof TextView) {
                holder.txtTitle = (TextView) view;
            }

            //            view = row.findViewById(R.id.textViewHoursBegin);
            //            if (view instanceof TextView) {
            //                holder.txtHoursBegin = (TextView) view;
            //            }
            //
            //            view = row.findViewById(R.id.textViewHoursEnd);
            //            if (view instanceof TextView) {
            //                holder.txtHoursEnd = (TextView) view;
            //            }
            //
            //            view = row.findViewById(R.id.textViewCategory);
            //            if (view instanceof TextView) {
            //                holder.txtCategory = (TextView) view;
            //            }
            //
            //            view = row.findViewById(R.id.textViewDescription);
            //            if (view instanceof TextView) {
            //                holder.txtDescription = (TextView) view;
            //            }

            view = row.findViewById(R.id.textViewPriority);
            if (view instanceof TextView) {
                holder.txtPriority = (TextView) view;
            }

            view = row.findViewById(R.id.textViewProportion);
            if (view instanceof TextView) {
                holder.txtProportion = (TextView) view;
            }

            row.setTag(holder);
        } else {
            holder = (TiHolder) row.getTag();
        }

        Ti ti = mList.get(position);

        holder.txtTitle.setText(ti.getTitle());
        //        holder.txtHoursBegin.setText(ti.getHoursBegin());
        //        holder.txtHoursEnd.setText(ti.getHoursEnd());
        //        holder.txtCategory.setText(ti.getCategory());
        //        holder.txtDescription.setText(ti.getDescription());
        holder.txtProportion.setText(PotmUtils.formatDouble(ti.getProportion())
                + "%");

        switch (ti.getPriority()) {
            case 5:
                holder.txtPriority.setText("Muito Alta");
                break;
            case 4:
                holder.txtPriority.setText("Alta");
                break;
            case 3:
                holder.txtPriority.setText("Média");
                break;
            case 2:
                holder.txtPriority.setText("Baixa");
                break;
            case 1:
                holder.txtPriority.setText("Muito Baixa");
                break;
            default:
                holder.txtPriority.setText("");
                break;
        }

        return row;

    }

    /**
     * The Class PlayerHolder.
     */
    static class TiHolder {

        TextView txtPriority;

        TextView txtTitle;

        //        TextView txtHoursBegin;
        //
        //        TextView txtHoursEnd;
        //
        //        TextView txtCategory;
        //
        //        TextView txtDescription;

        TextView txtProportion;

    }
}
