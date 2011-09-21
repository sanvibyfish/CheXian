package com.xrl.chexian.widget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xrl.chexian.R;
import com.xrl.chexian.Settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class ValidationEditText extends EditText implements TextWatcher {

	private static final boolean DEBUG = Settings.DEBUG;
	private static final String TAG = "ValidationEditText";
	private String regex;
	private String successedMessage;
	private String failedMessage;
	private boolean isSuccessed;

	public boolean isSuccessed() {
		return isSuccessed;
	}

	public void setSuccessed(boolean isSuccessed) {
		this.isSuccessed = isSuccessed;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public ValidationEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addTextChangedListener(this);
		try {
		/**
		* 跟values/attrs.xml里面定义的属性绑定
		*/
			TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ValidationEditText);
			this.regex = a.getString(R.styleable.ValidationEditText_regex);
			this.successedMessage = a.getString(R.styleable.ValidationEditText_successed_message);
			this.failedMessage = a.getString(R.styleable.ValidationEditText_failed_message);
			a.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ValidationEditText(Context context) {
		super(context);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern
				.compile(regex);
		Matcher matcher = pattern.matcher(s.toString());
		if (matcher.matches()) {
//			Toast.makeText(getContext(), successedMessage, Toast.LENGTH_SHORT)
//					.show();
			isSuccessed = true;
		} else {
			Toast.makeText(getContext(), failedMessage, Toast.LENGTH_SHORT)
					.show();
			isSuccessed = false;
		}
		if (DEBUG)
			Log.d(TAG, "afterTextChanged():" + s.toString());
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		if (DEBUG)
			Log.d(TAG, "beforeTextChanged():" + s.toString());
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (DEBUG)
			Log.d(TAG, "onTextChanged():" + s.toString());
	}

}
