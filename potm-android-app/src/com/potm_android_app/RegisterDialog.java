package com.potm_android_app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TimePicker;

public class RegisterDialog extends Dialog {

	
	private String mActicity;
	private String mHour;
	private String mMinutes;
	AutoCompleteTextView autoCompletetextView;
	private TimePicker time;
	Button buttonOk,buttonCancel;
	
	
	public RegisterDialog(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_dialog);
		buttonOk = (Button) findViewById(R.id.buttonOK);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		autoCompletetextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTitle);
		time = (TimePicker) findViewById(R.id.timePicker);
		time.setIs24HourView(true);
		onClickButtonCancel();
		onClickButtonOK();
	}

	
	public void onClickButtonCancel() {
		buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	dismiss();
            }
        });
	}
	
	public void onClickButtonOK() {
		buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mActicity = autoCompletetextView.getText().toString();
            	mHour = time.getCurrentHour().toString();
            	mMinutes = time.getCurrentMinute().toString();
            	dismiss();
            }
        });
	}
	
}

