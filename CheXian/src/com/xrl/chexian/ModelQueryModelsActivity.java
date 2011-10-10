package com.xrl.chexian;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xrl.chexian.adapter.ModelAdapter;
import com.xrl.chexian.adapter.ModelAdapter.ViewHolder;
import com.xrl.chexian.model.ModelQuery;
import com.xrl.chexian.utils.ActivityUtils;

public class ModelQueryModelsActivity extends Activity {

	private TextView textViewTitle;
	private ImageView imgTitleProgress;
	private ListView lvModels;
	private ModelAdapter adapter;
	private Button btnBack;
	private Button btnNext;
	public static final String SELECTED_MODEL = "selectedModel";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_query_models);
		initComponent();
	}

	private void initComponent() {
		textViewTitle = (TextView) findViewById(R.id.title_text_view);
		textViewTitle.setText("车型选择");
		imgTitleProgress = (ImageView) findViewById(R.id.title_progress);
		imgTitleProgress
				.setImageResource(R.drawable.progress_2);

		lvModels = (ListView) findViewById(R.id.model_query_models_list_view);
		Bundle bundle = getIntent().getExtras();
		final ModelQuery mq = (ModelQuery) bundle
				.getSerializable(ModelQueryActivity.MODEL_QUERY);
		adapter = new ModelAdapter(this, mq.models);
		lvModels.setAdapter(adapter);
		lvModels.setItemsCanFocus(false);
		btnBack = (Button) findViewById(R.id.footer_back_button);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityUtils.back(ModelQueryModelsActivity.this, getIntent());
			}
		});
		
		btnNext = (Button)findViewById(R.id.footer_next_button);
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(ModelQueryActivity.MODEL_QUERY, mq);
				if(adapter.positionSelected != null){
					bundle.putSerializable(SELECTED_MODEL, mq.models.get(adapter.positionSelected));
				}else{
					Toast.makeText(ModelQueryModelsActivity.this, "请选择一款车型", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(mq.otherInfoBlock == 0&& mq.supervisorBlock == 0 ){
					ActivityUtils.jump(ModelQueryModelsActivity.this, SaveApplyInfoActivity.class, ActivityUtils.SAVE_APPLY_INFO);
				}else{
					ActivityUtils.jump(ModelQueryModelsActivity.this, InfoPlusActivity.class, ActivityUtils.INFO_PLUS, bundle);
				}
			}
		});
	}
}
