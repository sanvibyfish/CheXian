package com.xrl.chexian.task;

import java.util.List;

import com.xrl.chexian.http.CheXianHttpApiV1;
import com.xrl.chexian.model.ModelQuery;
import com.xrl.chexian.model.Result;

import android.content.Context;

/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-9-2 下午01:27:45
 */
public class ChannelTask extends BaseTask implements IDataResult{

	public ModelQuery modelQuery = null;
	String licenseNo;
	String registerDate;
	String model;
	String bizQuoteBeginDate;
	String forceQuoteBeginDate;
	public ChannelTask(String preDialogMessage, Context context,String licenseNo, String registerDate,
			String model, String bizQuoteBeginDate, String forceQuoteBeginDate) {
		super(preDialogMessage, context);
	}

	@Override
	public void getData() throws Exception {
		// TODO Auto-generated method stub
		modelQuery = CheXianHttpApiV1.getInstance().getModelQuery(licenseNo,registerDate,
				model,bizQuoteBeginDate,forceQuoteBeginDate);
	}

	@Override
	public void onPreStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStateError() {
		// TODO Auto-generated method stub
	}

	@Override
	public Result getResult() {
		// TODO Auto-generated method stub
		return modelQuery;
	}


}
