package com.xrl.chexian.utils;

import com.xrl.chexian.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-8-25 上午11:48:00
 */
public class ActivityUtils {

	public static final int MODEL_QUERY_ACTIVITY = 10;
	public static final int CHE_XIAN_ACITVITY = 11;
	
	private static final String TAG = "ActivityUtils";
	private static final boolean DEBUG = Settings.DEBUG; 
	/**
	 * 窗体跳转
	 * @param old
	 * @param cls
	 */
	public static void jump(Context old, Class<?> cls, int requestCode,Bundle mBundle){
		jump(old, cls, requestCode,mBundle,false);
	}
	
	/**
	 * 窗体跳转
	 * @param old
	 * @param cls
	 */
	public static void jump(Context old, Class<?> cls, int requestCode,Bundle mBundle,boolean clearTop){
		   Intent intent = new Intent();  
           intent.setClass(old, cls);
           if(mBundle != null){
        	   intent.putExtras(mBundle);
           }
           
           Activity activity = (Activity) old;
           if(clearTop){
        	   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
        	   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           }
           activity.startActivityForResult(intent, requestCode); 
	}
	
	
	
	public static void jump(Context old, Class<?> cls, int requestCode){
		jump(old, cls, requestCode,null);
	}
	
	
	public static void back(Context old, Intent intent){
		   Activity activity = (Activity) old;
		   activity.setResult(Activity.RESULT_OK, intent);
		   activity.finish();
	}
	
	/**
	 * 添加控件(会删除之前layout所有控件)
	 * @param layout
	 * @param view
	 */
	public static void addViewOnly(ViewGroup layout, View view){
		try {
			if(layout.getChildCount() > 0){
				layout.removeAllViews();
			}
			layout.addView(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void displayImage(Context context, String url,ImageView imageView){
		imageView.setTag(url);
		ImageLoader.getInstance(context).displayImage(url, imageView);
//			BitmapManager.getInstance().fetchDrawableOnThread(url, imageView);
	}
	public static void runInUIThread(Context context, final Toast toast){
		final Activity activity = (Activity)context;
	      activity.runOnUiThread(new Runnable() {
	           public void run() {
	        	   toast.show();
	           }
	       });
	}
	
	public static Display getWindowDisplay(Context context){
		return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
	}
	
	public static String getPhoneNumber(Context context){
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = telephonyManager.getLine1Number();
		if(DEBUG)Log.d(TAG,"phone number:" + phoneNumber);
		return phoneNumber;  
	}
	
	public static String getDeviceId(Context context){
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	public static String getString(Context context,final int mStringId){
		return context.getResources().getString(mStringId);
	}
	
	public static int getColor(Context context,final int mColorId){
		return context.getResources().getColor(mColorId);
	}
	
	public static float getPX(Context context, int dipValue){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
	}
}
