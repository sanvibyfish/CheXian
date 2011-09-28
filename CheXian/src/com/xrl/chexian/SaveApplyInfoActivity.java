package com.xrl.chexian;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SaveApplyInfoActivity extends Activity {

	private TextView textViewTitle;
	private ImageView imgTitleProgress;

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
	}
	
}
