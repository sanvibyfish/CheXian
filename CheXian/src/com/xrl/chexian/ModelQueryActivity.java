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
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xrl.chexian.model.City;
import com.xrl.chexian.model.ModelQuery;
import com.xrl.chexian.task.ModelQueryTask;
import com.xrl.chexian.utils.ActivityUtils;
import com.xrl.chexian.utils.StringUtils;
import com.xrl.chexian.widget.ValidationEditText;

public class ModelQueryActivity extends Activity {

	List<City> cities;
	private Spinner spQueryModelCity;
	private ArrayAdapter<String> adapter;
	private Button btnRegisterDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_REGISTER = 1;
	static final int DATE_BISINESS_INSURANCE = 2;
	static final int DATE_INSURANCE = 3;

	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 车牌
	 */
	private String licenseNo;
	/**
	 * 登记年月
	 */
	private String registerDate;
	/**
	 * 行驶证车辆型号
	 */
	private String model;

	/**
	 * 商业险保险起期
	 */
	private String bizQuoteBeginDate;
	/**
	 * 交强险保险起期
	 */
	private String forceQuoteBeginDate;
	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;
	
	private DatePickerDialog.OnDateSetListener mBusinessInsuranceDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			btnBusinessInsurance.setText(new StringBuilder().append(mYear)
					.append("-").append(mMonth + 1).append("-").append(mDay));
		}
	};

	private DatePickerDialog.OnDateSetListener mInsuranceDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			btnInsurance.setText(new StringBuilder().append(mYear).append("-")
					.append(mMonth + 1).append("-").append(mDay));
		}
	};

	private DatePickerDialog.OnDateSetListener mRegisterDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			btnRegisterDate.setText(new StringBuilder().append(mYear)
					.append("-").append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)));

		}
	};
	private Button btnBusinessInsurance;
	private Button btnInsurance;
	private Button btnBack;
	private Button btnNewCard;
	private boolean isNewCard;
	private Button btnNextStep;
	private EditText editTextLicenseNo;
	private EditText editTextModel;
	private ModelQueryTask modelQueryTask;
	private EditText editTextMobile;
	private ValidationEditText editTextEmail;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_REGISTER:
			return new DatePickerDialog(this, mRegisterDateSetListener, mYear,
					mMonth, mDay);
		case DATE_BISINESS_INSURANCE:
			return new DatePickerDialog(this,
					mBusinessInsuranceDateSetListener, mYear, mMonth, mDay);
		case DATE_INSURANCE:
			return new DatePickerDialog(this, mInsuranceDateSetListener, mYear,
					mMonth, mDay);
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
		
		editTextMobile = (EditText) findViewById(R.id.model_query_mobile_edit_text);
		editTextEmail = (ValidationEditText) findViewById(R.id.model_query_email_edit_text);
		spQueryModelCity = (Spinner) findViewById(R.id.query_model_city_sp);
		// adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
		// cities);
		// spQueryModelCity.setAdapter(adapter);
		btnBusinessInsurance = (Button) findViewById(R.id.model_query_business_insurance_button);
		btnBusinessInsurance.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showDialog(DATE_BISINESS_INSURANCE);
			}
		});

		btnInsurance = (Button) findViewById(R.id.model_query_insurance_button);
		btnInsurance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_INSURANCE);
			}
		});

		btnRegisterDate = (Button) findViewById(R.id.model_query_register_date_button);
		btnRegisterDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_REGISTER);
			}
		});

		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityUtils.back(ModelQueryActivity.this, getIntent());
			}
		});

		btnNewCard = (Button) findViewById(R.id.model_query_new_card_button);
		btnNewCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isNewCard) {
					isNewCard = true;
					v.setBackgroundResource(R.drawable.model_query_new_card_pressed);
				} else {
					isNewCard = false;
					v.setBackgroundResource(R.drawable.model_query_new_card_normal);

				}
			}
		});

		btnNextStep = (Button) findViewById(R.id.btnNextStep);
		editTextLicenseNo = (EditText) findViewById(R.id.model_query_license_no);
		editTextModel = (EditText) findViewById(R.id.model_query_code_edit_text);
		btnNextStep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				licenseNo = editTextLicenseNo.getText().toString();
				cityCode = "110100";
				registerDate = btnRegisterDate.getText().toString();
				model = editTextModel.getText().toString();
				bizQuoteBeginDate = btnBusinessInsurance.getText().toString();
				forceQuoteBeginDate = btnInsurance.getText().toString();
				mobile = editTextMobile.getText().toString();
				email = editTextEmail.getText().toString();
				modelQueryTask = new ModelQueryTask("",
						ModelQueryActivity.this, cityCode, licenseNo,
						registerDate, model, bizQuoteBeginDate,
						forceQuoteBeginDate,mobile,email);
				modelQueryTask.execute(new Runnable() {
					@Override
					public void run() {
						ModelQuery modelQuery = (ModelQuery) modelQueryTask.getResult();
						Toast.makeText(ModelQueryActivity.this, "ddd", Toast.LENGTH_SHORT).show();
					}
				});

			}
		});
	}
}
