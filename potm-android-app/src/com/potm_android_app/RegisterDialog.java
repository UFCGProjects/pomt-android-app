package com.potm_android_app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class RegisterDialog extends Dialog {

	
	private String mActicity;
	private Integer horasInicio;
	private Integer minutosInicio;
	AutoCompleteTextView autoCompletetextView;
	private TimePicker timeInicio;
	private TimePicker timeFim;
	Button buttonOk,buttonCancel;
	protected Integer horasFim;
	protected Integer minutosFim;
	
	
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
		timeInicio = (TimePicker) findViewById(R.id.timePickerInicio);
		timeInicio.setIs24HourView(true);
		timeFim = (TimePicker) findViewById(R.id.timePickerFinal);
		timeFim.setIs24HourView(true);
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
            	horasInicio = timeInicio.getCurrentHour();
            	minutosInicio = timeInicio.getCurrentMinute();
            	horasFim = timeFim.getCurrentHour();
            	minutosFim = timeFim.getCurrentMinute();
            	DateTime horaInicio = new DateTime().withTime(horasInicio, minutosInicio, 0, 0);
            	DateTime horaFinal = new DateTime().withTime(horasFim, minutosFim, 0, 0);
            	if (Hours.hoursBetween(horaInicio, horaFinal).getHours() < 0 || 
            	   ((Hours.hoursBetween(horaInicio, horaFinal).getHours() == 0) &&
            	    (Minutes.minutesBetween(horaInicio, horaFinal).getMinutes() <= 0))) {//horairo final < horario inicial
                    Toast.makeText(getContext(), "Horário Final Inválido",Toast.LENGTH_LONG).show();
				}else if (mActicity.length() == 0) { // nao digitou o nome da atividade
					Toast.makeText(getContext(), "Indique a Atividade",Toast.LENGTH_LONG).show();
				}else{
					dismiss();
				}
            	
            }
        });
	}
	
}

