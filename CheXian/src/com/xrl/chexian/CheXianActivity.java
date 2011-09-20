package com.xrl.chexian;

import com.xrl.chexian.utils.ActivityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CheXianActivity extends Activity {
    private Button btnQuote;
    
    //sadasdasdas
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initComponent();
    }

	private void initComponent() {
		btnQuote = (Button) findViewById(R.id.quote_button);
		btnQuote.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				ActivityUtils.jump(CheXianActivity.this, ModelQueryActivity.class, ActivityUtils.MODEL_QUERY_ACTIVITY);
			}
		});
	}
    
    
}