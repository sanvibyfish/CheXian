package com.xrl.chexian;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.xrl.chexian.model.City;
import com.xrl.chexian.utils.StringUtils;

public class ModelQueryActivity extends Activity {

	List<City> cities;
	private Spinner spQueryModelCity;
	private ArrayAdapter<String> adapter;
	private Button btnRegisterDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 1;
	static final int DATE_INSURANCE = 2;
	private void updateDisplay(Button btn) {
		btn.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1));
	}

	private DatePickerDialog.OnDateSetListener mInsuranceDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay(btnRegisterDate);
		}
	};
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay(btnInsurance);
		}
	};
	private Button btnInsurance;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case DATE_INSURANCE:
			return new DatePickerDialog(this, mInsuranceDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_query);

		InputStream is = getResources().openRawResource(R.raw.city);
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<City>>() {
		}.getType();
		cities = new Gson().fromJson(StringUtils.convertStreamToString(is),
				type);

		initComponent();
		final Calendar c = Calendar.getInstance(Locale.CHINA);

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

	}

	private void initComponent() {
		spQueryModelCity = (Spinner) findViewById(R.id.query_model_city_sp);
		// adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
		// cities);
		// spQueryModelCity.setAdapter(adapter);
		btnInsurance = (Button) findViewById(R.id.model_query_insurance_button);
		btnInsurance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_INSURANCE);
			}
		});
		
		btnRegisterDate = (Button) findViewById(R.id.model_query_register_date_button);
		btnRegisterDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}
}