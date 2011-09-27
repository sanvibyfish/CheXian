package com.xrl.chexian.adapter;

import java.util.List;

import com.xrl.chexian.R;
import com.xrl.chexian.Settings;
import com.xrl.chexian.model.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-9-2 下午01:20:31
 */
public class ModelAdapter extends BaseAdapter {


	private Context context;

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
	 public boolean isSelected;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	   // TODO Auto-generated method stub
	   ViewHolder holder = null;
	  
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
	   
	   convertView.setTag(holder);
	   return convertView;
	}

}
