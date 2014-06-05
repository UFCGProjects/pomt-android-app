package com.potm_android_app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.json.JSONObject;

import com.potm_android_app.utils.PotmUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
                    Toast.makeText(getContext(), "Hor�rio Final Inv�lido",Toast.LENGTH_LONG).show();
				}else if (mActicity.length() == 0) { // nao digitou o nome da atividade
					Toast.makeText(getContext(), "Indique a Atividade",Toast.LENGTH_LONG).show();
				}else{
					new JSONParse().execute();
					dismiss();
				}
            	
            }
        });
	}
	
    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username", "developer"));
	        nameValuePairs.add(new BasicNameValuePair("date_end", horasFim.toString()));
            nameValuePairs.add(new BasicNameValuePair("date_begin",horasFim.toString()));
            nameValuePairs.add(new BasicNameValuePair("category", "Teste"));
            nameValuePairs.add(new BasicNameValuePair("description", "Testando envio"));
            nameValuePairs.add(new BasicNameValuePair("title", mActicity));

            JSONObject json = jParser.postData(PotmUtils.getServerURL(),
                    nameValuePairs);

            if (json == null) {
                Log.d("POMT", "error!!!!");
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Toast.makeText(getContext(), "enviou os dados",
                    Toast.LENGTH_LONG).show();

        }

    }

	
	
	
}

