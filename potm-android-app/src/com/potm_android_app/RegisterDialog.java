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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import com.potm_android_app.utils.PotmUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
	private List<String> titles;
	
	
	public RegisterDialog(Context context) {
		super(context);
	}

	public RegisterDialog(Context mainActivity, List<String> allTitles) {
		super(mainActivity);
		titles = allTitles;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_dialog);
		buttonOk = (Button) findViewById(R.id.buttonOK);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		autoCompletetextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTitle);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,titles);
        autoCompletetextView.setAdapter(adapter);
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
            	DateTime horarioInicial = new DateTime().withTime(horasInicio, minutosInicio, 0, 0);
            	DateTime horarioFinal = new DateTime().withTime(horasFim, minutosFim, 0, 0);
            	
            	if (horarioInicial.isAfter(horarioFinal) || horarioInicial.equals(horarioFinal)) {//horairo final < horario inicial
                    Toast.makeText(getContext(), "Horário Final Inválido",Toast.LENGTH_LONG).show();
				}else if (mActicity.length() == 0) { // nao digitou o nome da atividade
					Toast.makeText(getContext(), "Indique a Atividade",Toast.LENGTH_LONG).show();
				}else{
					
					Log.v("HORA", String.valueOf(horarioFinal));
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
	        nameValuePairs.add(new BasicNameValuePair("date_end", String.valueOf(new GregorianCalendar(2014, 5, 6, horasInicio, minutosInicio).getTimeInMillis())));
            nameValuePairs.add(new BasicNameValuePair("date_begin",String.valueOf(new GregorianCalendar(2014, 5, 6, horasFim, minutosFim).getTimeInMillis())));
            nameValuePairs.add(new BasicNameValuePair("category", "Sem necessidade"));
            nameValuePairs.add(new BasicNameValuePair("description", "Sem necessidade"));
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

