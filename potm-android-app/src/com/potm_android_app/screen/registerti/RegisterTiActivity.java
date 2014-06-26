
package com.potm_android_app.screen.registerti;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.potm_android_app.MainActivity;
import com.potm_android_app.R;
import com.potm_android_app.asynctask.PostJSONTask;
import com.potm_android_app.asynctask.PostJSONTask.PostJSONInterface;
import com.potm_android_app.utils.MyLog;
import com.potm_android_app.utils.PotmUtils;

public class RegisterTiActivity extends Activity implements PostJSONInterface {

    private ProgressDialog mDialog;
    private TextView mTimeBegin;
    private TextView mTimeEnd;
    private EditText mTitle;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ti);

        View view = findViewById(R.id.textViewTimeBegin);

        if (view instanceof TextView) {
            mTimeBegin = (TextView) view;
        }

        view = findViewById(R.id.textViewTimeEnd);

        if (view instanceof TextView) {
            mTimeEnd = (TextView) view;
        }

        view = findViewById(R.id.editTextTitle);

        if (view instanceof EditText) {
            mTitle = (EditText) view;
        }

        view = findViewById(R.id.buttonRegisterTi);

        if (view instanceof Button) {
            mButton = (Button) view;
        }

        mTimeBegin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DateTime date = new DateTime();

                int hourOfDay = date.getHourOfDay();
                int minute = date.getMinuteOfHour();

                TimePickerDialog dialog = new TimePickerDialog(RegisterTiActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                setTimeBegin(hourOfDay, minute);
                            }
                        }, hourOfDay, minute, true);

                dialog.setTitle(R.string.time_begin);
                dialog.show();
            }
        });

        DateTime date = new DateTime();
        mTimeBegin.setText(date.getHourOfDay() + ":" + date.getMinuteOfHour());

        view = findViewById(R.id.textViewTimeEnd);

        mTimeEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DateTime date = new DateTime();
                date = date.plusHours(2);

                int hourOfDay = date.getHourOfDay();
                int minute = date.getMinuteOfHour();

                TimePickerDialog dialog = new TimePickerDialog(RegisterTiActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                setTimeEnd(hourOfDay, minute);
                            }
                        }, hourOfDay, minute, true);

                dialog.setTitle(R.string.time_end);
                dialog.show();
            }
        });

        date = new DateTime();
        date = date.plusHours(2);
        mTimeEnd.setText(date.getHourOfDay() + ":" + date.getMinuteOfHour());

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registerTi();
            }
        });
    }

    protected void registerTi() {
        DateTime begin = null;
        DateTime end = null;
        DateTime now = new DateTime();

        if (mTimeBegin != null) {
            String[] split = mTimeBegin.getText().toString().split(":");
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(),
                    Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }

        if (mTimeEnd != null) {
            String[] split = mTimeEnd.getText().toString().split(":");
            end = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(),
                    Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        }

        if ((begin != null) && (end != null)) {
            if (end.isBefore(begin)) {
                PotmUtils.showToast(this, "Hora final deve ser antes que o horário inicial");
            } else if ((mTitle != null) && (mTitle.getText().toString().trim().length() > 4)) {
                sendPost(mTitle.getText().toString().trim(), begin, end);
            }
        }
    }

    private void sendPost(String t, DateTime begin, DateTime end) {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle(R.string.adicionando_ti);
        mDialog.setMessage(getResources().getString(R.string.please_wait));
        mDialog.setCancelable(false);
        mDialog.show();

        String title = "title=" + t;
        String description = "description=" + "Default";
        String category = "category=" + "Default";
        String date_begin = "date_begin=" + begin.getMillis();
        String date_end = "date_end=" + end.getMillis();
        String username = "username=" + "developer";

        new PostJSONTask(this).execute(title + "&" + description + "&" + category + "&"
                + date_begin + "&" + date_end + "&" + username);
    }

    public void setTimeBegin(int hour, int minute) {
        View view = findViewById(R.id.textViewTimeBegin);

        if (view instanceof TextView) {
            ((TextView) view).setText(hour + ":" + minute);
        }
    }

    public void setTimeEnd(int hour, int minute) {
        View view = findViewById(R.id.textViewTimeEnd);

        if (view instanceof TextView) {
            ((TextView) view).setText(hour + ":" + minute);
        }
    }

    @Override
    public void callbackPostJSON(JSONObject json) {
        mDialog.dismiss();

        String status;
        try {
            status = json.getString("status");

            if (status.equalsIgnoreCase("success")) {
                PotmUtils.showToast(this, "Ti adicionado com sucesso.");
                startActivity(new Intent(RegisterTiActivity.this, MainActivity.class));
            } else {
                PotmUtils.showToast(this, "Falha ao adicionar o Ti");
            }
        } catch (JSONException e) {
            MyLog.error("Error when geting status from json", e);
        }

    }

}
