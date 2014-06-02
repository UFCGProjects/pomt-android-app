package com.potm_android_app;

import com.potm_android_app.model.Ti;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class RegisterTi extends Activity {

	
	
	private String mActicity;
	private String mHour;
	private String mMinutes;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_ti);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		TextView textView;
		TimePicker time;
		switch (item.getItemId()) {
		case R.id.autoCompleteTitle: // FALTA RECUPERAR OS DADOS DO SERVIDOR PARA FAZER O AUTOCOMPLETE
			textView = (TextView) findViewById(R.id.autoCompleteTitle);
			mActicity = (String) textView.getText ();
			return true;
		
		case R.id.timePicker:
			time = (TimePicker) findViewById(R.id.timePicker);
			mHour = time.getCurrentHour().toString();
			mMinutes = time.getCurrentMinute().toString();
			return true;
		
		case R.id.buttonOK:
			if (mActicity == null) {
				return false;
			}
			Ti newTi = new Ti(mActicity, mHour, mMinutes);
			//ENVIAR AO BD
			Toast.makeText(getBaseContext(), "Ti Registrada",
					Toast.LENGTH_LONG).show();
			return true;
		
		case R.id.buttonCancel:

			return true;
		}
		
		
		
		
		
		
		return super.onOptionsItemSelected(item);
	}
	
}
