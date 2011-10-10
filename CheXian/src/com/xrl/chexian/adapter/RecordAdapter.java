package com.xrl.chexian.adapter;

import java.io.InputStream;
import java.util.List;

import com.google.gson.Gson;
import com.xrl.chexian.R;
import com.xrl.chexian.Settings;
import com.xrl.chexian.model.City;
import com.xrl.chexian.model.Option;
import com.xrl.chexian.utils.ActivityUtils;
import com.xrl.chexian.utils.StringUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-9-2 下午01:20:31
 */
public class RecordAdapter extends ArrayAdapter  {

	private Context context;

	private List<Option> options;
	
	public RecordAdapter(Context context,List<Option> options)
	{
		super(context, R.layout.record_item, options);
		this.options = options;
		mInflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private LayoutInflater mInflater = null;
	private static final String TAG = "BizQuoteClaimRecordAdapter";
	private static final boolean DEBUG = Settings.DEBUG;

	@Override
	public int getCount() {
		if (options != null) {
			return options.size();
		} else {
			return 0;
		}

	}

	@Override
	public Option getItem(int position) {
		return options.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView textViewOption;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.city_item, null);
			holder.textViewOption = (TextView) convertView
					.findViewById(R.id.city_text_view);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (DEBUG)
			Log.d(TAG, "this position is :" + position);
		Option option = options.get(position);
		holder.textViewOption.setText(option.text);
		convertView.setTag(holder);
		return convertView;
	}

}
