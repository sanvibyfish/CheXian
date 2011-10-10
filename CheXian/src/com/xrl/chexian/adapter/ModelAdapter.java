package com.xrl.chexian.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xrl.chexian.R;
import com.xrl.chexian.Settings;
import com.xrl.chexian.model.Model;


/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-9-2 下午01:20:31
 */
public class ModelAdapter extends BaseAdapter {


	private Context context;
	 ViewHolder holder = null;
	public Integer positionSelected;
	public ModelAdapter(Context c ,List<Model> models) {
	   this.context = c;
	   this.models = models;
	   mInflater = (LayoutInflater) c
	     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private List<Model> models = null;
	private LayoutInflater mInflater = null;
	private static final String TAG = "ChannelAdapter";
	private static final boolean DEBUG = Settings.DEBUG;

	@Override
	public int getCount() {
	   if (models != null) {
	    return models.size();
	   } else {
	    return 0;
	   }

	}

	@Override
	public Model getItem(int position) {
	   return models.get(position);
	}

	@Override
	public long getItemId(int position) {
	   return position;
	}

	public static class ViewHolder {
	 public TextView textViewFamilyName;
	 public TextView textViewBrandName;
	 public TextView textViewPirce;
	 public TextView textViewExhaustMeasure;
	 public TextView textViewGearboxName;
	 public LinearLayout linearLayoutBg;
	 public Button button;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	   // TODO Auto-generated method stub
	  
	  
	   if (convertView == null) {
	    holder = new ViewHolder();
	    convertView = mInflater.inflate(R.layout.model_query_models_item, null);
	    holder.textViewFamilyName = (TextView) convertView
	      .findViewById(R.id.models_item_family_name_text_view);
	    holder.textViewBrandName = (TextView) convertView
	      .findViewById(R.id.models_item_brand_name_text_view);
	    holder.textViewPirce = (TextView) convertView
	  	      .findViewById(R.id.models_item_price_text_view);
	    holder.textViewExhaustMeasure = (TextView) convertView
	  	      .findViewById(R.id.models_item_exhaust_measure);
	    holder.textViewGearboxName = (TextView) convertView
		  	      .findViewById(R.id.models_item_gearbox_name);
	    holder.linearLayoutBg = (LinearLayout) convertView.findViewById(R.id.models_item_bg);
	    holder.button =  (Button) convertView.findViewById(R.id.models_item_button);
	   } else {
		   holder = (ViewHolder) convertView.getTag();
	   }
	   
	   if(position == 0){
		   holder.linearLayoutBg.setBackgroundResource(R.drawable.item_top_button_normal);
	   }else if(position == getCount()-1){
		   holder.linearLayoutBg.setBackgroundResource(R.drawable.item_bottom_button_normal);
	   }
	   
	   if(DEBUG)Log.d(TAG, "this position is :" + position);
	   Model model = models.get(position);
	   holder.textViewFamilyName.setText(model.familyName);
	   holder.textViewBrandName.setText(model.brandName);
	   holder.textViewPirce.setText(model.price);
	   holder.textViewExhaustMeasure.setText(model.exhaustMeasure);
	   holder.textViewGearboxName.setText(model.gearboxName);
	   if(positionSelected != null && positionSelected == position){
		   holder.button.setBackgroundResource(R.drawable.radio_selected);
	   }else{
		   holder.button.setBackgroundResource(R.drawable.radio_normal);
	   }
	   holder.button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			positionSelected = position;
			notifyDataSetChanged();
			if(DEBUG)Log.d(TAG,"the position:" + position + " is selected");			
		}
	});
	   convertView.setTag(holder);
	   return convertView;
	}

}
