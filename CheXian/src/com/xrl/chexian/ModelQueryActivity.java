package com.xrl.chexian;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xrl.chexian.adapter.CityAdapter;
import com.xrl.chexian.model.City;
import com.xrl.chexian.model.ModelQuery;
import com.xrl.chexian.task.ModelQueryTask;
import com.xrl.chexian.utils.ActivityUtils;
import com.xrl.chexian.utils.StringUtils;
import com.xrl.chexian.widget.ValidationEditText;

public class ModelQueryActivity extends Activity {
	private static final String TAG = "ModelQueryActivity";
	private static final boolean DEBUG = Settings.DEBUG;
	List<City> cities;
	private Button btnQueryModelCity;
	private ListAdapter  cityAdapter;
	private Button btnRegisterDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_REGISTER = 1;
	static final int DATE_BISINESS_INSURANCE = 2;
	static final int DATE_INSURANCE = 3;
	final Calendar c = Calendar.getInstance(Locale.CHINA);

	private City currentCity;
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
			int nowDay = c.get(Calendar.DAY_OF_MONTH);
			if(nowDay == mDay){
				mDay += 2;
				Toast.makeText(ModelQueryActivity.this, "商业险起期通常为上年保单到期日后第二天",Toast.LENGTH_SHORT).show();
				btnBusinessInsurance.setText(new StringBuilder().append(mYear)
						.append("-").append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)).append("-").append(mDay));
			}else if(nowDay > mDay){
				mDay = nowDay;
				Toast.makeText(ModelQueryActivity.this, "不能选择今天之前的日期",Toast.LENGTH_SHORT).show();
				btnBusinessInsurance.setText(new StringBuilder().append(mYear)
						.append("-").append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)).append("-").append(mDay));
			}else{
				btnBusinessInsurance.setText(new StringBuilder().append(mYear)
						.append("-").append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)).append("-").append(mDay));
			}
		}
	};

	private DatePickerDialog.OnDateSetListener mInsuranceDateSetListener = new DatePickerDialog.OnDateSetListener() {



		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			if(DEBUG)Log.d(TAG,"now day:" +c.get(Calendar.DAY_OF_MONTH));
			int nowDay = c.get(Calendar.DAY_OF_MONTH);
			if(nowDay == mDay){
				mDay += 2;
				Toast.makeText(ModelQueryActivity.this, "商业险起期通常为上年保单到期日后第二天",Toast.LENGTH_SHORT).show();
				btnInsurance.setText(new StringBuilder().append(mYear).append("-")
						.append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)).append("-").append(mDay));
			}else if(nowDay > mDay){
				mDay = nowDay;
				Toast.makeText(ModelQueryActivity.this, "不能选择今天之前的日期",Toast.LENGTH_SHORT).show();
				btnInsurance.setText(new StringBuilder().append(mYear)
						.append("-").append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)).append("-").append(mDay));
			}else{
				btnInsurance.setText(new StringBuilder().append(mYear).append("-")
						.append((mMonth + 1)>10?(mMonth + 1):"0" + (mMonth + 1)).append("-").append(mDay));
			}
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
	private Button btnHome;
	private Button btnNewCard;
	private boolean isNewCard;
	private Button btnNextStep;
	private ValidationEditText editTextLicenseNo;
	private EditText editTextModel;
	private ModelQueryTask modelQueryTask;
	private EditText editTextMobile;
	private ValidationEditText editTextEmail;
	private Button btnBack;

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
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		initComponent();

	}

	private void initComponent() {
		
		editTextMobile = (EditText) findViewById(R.id.model_query_mobile_edit_text);
		editTextEmail = (ValidationEditText) findViewById(R.id.model_query_email_edit_text);
		btnQueryModelCity = (Button) findViewById(R.id.query_model_city_button);
		InputStream is = getResources().openRawResource(R.raw.city);
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<City>>() {
		}.getType();
		cities = new Gson().fromJson(StringUtils.convertStreamToString(is),
				type);
		cityAdapter = new CityAdapter(this, cities);
		btnQueryModelCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final AlertDialog.Builder ad = new AlertDialog.Builder(ModelQueryActivity.this);
				ad.setSingleChoiceItems(cityAdapter, -1,  new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// a choice has been made!
						currentCity = (City) cityAdapter.getItem(which);
						Log.d("ModelQueryActivity", "chosen " + currentCity.text );
						btnQueryModelCity.setText(currentCity.text);
						editTextLicenseNo.setText(currentCity.carNo);
						dialog.dismiss();
					}
				});
				ad.show();
			}
		});
		
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

//		btnHome = (Button) findViewById(R.id.home_button);
//		btnHome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ActivityUtils.jump(ModelQueryActivity.this,CheXianActivity.class,ActivityUtils.CHE_XIAN_ACITVITY,getIntent().getExtras(),true);
//			}
//		});

		btnNewCard = (Button) findViewById(R.id.model_query_new_card_button);
		btnNewCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isNewCard) {
					isNewCard = true;
					v.setBackgroundResource(R.drawable.model_query_new_card_pressed);
					if(currentCity != null){
						editTextLicenseNo.setText(currentCity.carNo + "*");
						editTextLicenseNo.setEnabled(false);
					}
				} else {
					isNewCard = false;
					v.setBackgroundResource(R.drawable.model_query_new_card_normal);
					editTextLicenseNo.setEnabled(true);

				}
			}
		});
		
//		btnBack = (Button) findViewById(R.id.left_button);
//		btnBack.setVisibility(View.INVISIBLE);
		
		btnNextStep = (Button) findViewById(R.id.next_step_button);
		editTextLicenseNo = (ValidationEditText) findViewById(R.id.model_query_license_no);
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
