package com.xrl.chexian;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class SaveApplyInfoActivity extends Activity {

	private TextView textViewTitle;
	private ImageView imgTitleProgress;
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_apply_info);
		initComponent();
	}

	private void initComponent() {
		textViewTitle = (TextView) findViewById(R.id.title_text_view);
		textViewTitle.setText("投保信息");
		imgTitleProgress = (ImageView) findViewById(R.id.title_progress);
		imgTitleProgress
				.setImageResource(R.drawable.save_apply_info_progress);
		
		radioGroup = (RadioGroup) findViewById(R.id.save_apply_infi_radio_is_car_own_group);
		final LinearLayout layoutEmail = (LinearLayout) findViewById(R.id.save_apply_info_email_layout);
		final LinearLayout layoutMobile = (LinearLayout) findViewById(R.id.save_apply_info_mobile_layout);
		final LinearLayout layoutIdCardNo = (LinearLayout) findViewById(R.id.save_apply_info_id_card_no_layout);
		final ImageView line = (ImageView) findViewById(R.id.save_apply_info_after_card_no_line);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.save_apply_info_radio_is_car_own_yes){
					line.setVisibility(View.GONE);
					layoutEmail.setVisibility(View.GONE);
					layoutMobile.setVisibility(View.GONE);
					layoutIdCardNo.setBackgroundResource(R.drawable.query_model_bottom);
				}else{
					line.setVisibility(View.VISIBLE);
					layoutIdCardNo.setBackgroundResource(R.drawable.query_model_middle);
					layoutMobile.setVisibility(View.VISIBLE);
					layoutEmail.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
}
