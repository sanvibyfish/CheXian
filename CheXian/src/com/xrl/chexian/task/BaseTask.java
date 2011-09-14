package com.xrl.chexian.task;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.json.JSONException;

import com.xrl.chexian.R;
import com.xrl.chexian.Settings;
import com.xrl.chexian.error.CheXianException;
import com.xrl.chexian.model.MsgResult;
import com.xrl.chexian.utils.ActivityUtils;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;



/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间2010-8-19 上午11:54:22
 */
public abstract class BaseTask extends AsyncTask<Runnable, Void, MsgResult> {
	private static final int STATE_FINISH = 1;
	private static final int STATE_ERROR = 2;
	protected static final String PROGRESS_DIALOG = null;
	private static ProgressDialog progressDialog = null;
	protected Context context = null;
	private String preDialogMessage = null;
	
	private static String TAG = "BaseTask";
	private static boolean DEBUG = Settings.DEBUG;
	/* 该方法将在执行实际的后台操作前被UI thread调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条ￄ1�7*/
	public BaseTask(String preDialogMessage, Context context){
		this.preDialogMessage = preDialogMessage;
		this.context = context;
	}
	@Override
	 protected void onPreExecute() {
		if(preDialogMessage != null){
			progressDialog = new ProgressDialog(context);
			progressDialog.setTitle("");
			progressDialog.setMessage(preDialogMessage);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}
		onPreStart();
	}

	/* 执行那些很�1�7�时的后台计算工作�1�7�可以调用publishProgress方法来更新实时的任务进度*/
	@Override
	protected MsgResult doInBackground(Runnable... runnables) {
		MsgResult result = new MsgResult();
		try {
			getData();
			result.successed = true;
			result.finishRunnable = runnables[0];
		}catch(CheXianException cheXianException){
			result.message = cheXianException.getExtra();
			result.exception = cheXianException;
			return result;
		}catch(SocketTimeoutException soe){
			if(DEBUG)soe.printStackTrace();
			result.message = ActivityUtils.getString(context, R.string.text_timeout_error);
			result.exception = soe;
			return result; 
		}catch(JSONException jsonException){
			if(DEBUG)jsonException.printStackTrace();
			result.message = ActivityUtils.getString(context, R.string.text_data_parse_error);
			result.exception = jsonException;
			return result;
		}catch (Exception e) {
			if(DEBUG)e.printStackTrace();
			result.message = ActivityUtils.getString(context, R.string.text_unknown_error);
			result.exception = e;
			return result;
		}
		return result;
	}
	
	/*
	 * 在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用ￄ1�7*
	 * 后台的计算结果将通过该方法传递到UI thread.
	 */
	@Override
	protected void onPostExecute(MsgResult result) {
		if(result.successed){
			if(preDialogMessage != null){
				progressDialog.dismiss();
			}
			onStateFinish(result.finishRunnable);
		}else{
			if(preDialogMessage != null){
				progressDialog.dismiss();
			}
			ActivityUtils.runInUIThread(context, Toast.makeText(context, result.message, Toast.LENGTH_SHORT));
			onStateError();
		}
	}
	
	/**
	 * 获取数据
	 */
	abstract public void getData() throws Exception;
	
	/**
	 * callback
	 */
	public void onStateFinish(Runnable runnable){
		runnable.run();
	}
	/**
	 * error
	 */
	abstract public void onStateError();
	/**
	 * 
	 */
	abstract public void onPreStart();
	
}
