package com.xrl.chexian;

import com.xrl.chexian.model.Input;
import com.xrl.chexian.model.ModelQuery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class InfoPlusActivity extends Activity{

	private EditText editTextBizQuoteKilometrePerYear;
	private LinearLayout layoutBizQuoteKilometrePerYear;
	private LinearLayout layoutBizQuoteSpecialCarFlag;
	private LinearLayout layoutBizQuoteSpecialCarDate;
	private LinearLayout layoutBizQuoteOfferLastPolicy;
	private LinearLayout layoutBizQuoteLastPolicyNo;
	private LinearLayout layoutBizQuoteClaimRecord;
	private LinearLayout layoutOtherInfoBlock;
	private LinearLayout layoutForceQuoteLastPolicyPremium;
	private LinearLayout layoutForceQuoteClaimRecord;
	private LinearLayout layoutForceQuoteHasDrinkDriveRecord;
	private LinearLayout layoutForceQuoteDrinkDriveTimes;
	private LinearLayout layoutForceQuoteDrunkDriveTimes;
	private LinearLayout layoutForceQuoteLastPolicyNo;
	private LinearLayout layoutTaxFormHasTax;
	private LinearLayout layoutTaxFormTaxApproach;
	private LinearLayout layoutTaxFormLastForcePolicyNo;
	private LinearLayout layoutTaxFormPayTaxNo;
	private LinearLayout layoutForceQuote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_plus);
		initComponent();
	}

	private void initComponent() {
		Bundle bundle = getIntent().getExtras();
		final ModelQuery mq = (ModelQuery) bundle
				.getSerializable(ModelQueryActivity.MODEL_QUERY);		
		
//		 其它信息块(含监管等其它信息),0不需要,大于0需要 
//		 	可能存在的录入项：
//		 	  年平均行驶里程：bizQuote.kilometrePerYear
//		 	  是否是过户车：bizQuote.specialCarFlag
//		 	  过户日期：bizQuote.specialCarDate
//		 	  用户是否能提供上年商业险保单：bizQuote.offerLastPolicy
//		 	  上年商业险险保单号：bizQuote.lastPolicyNo
//		 	  商业险理赔记录：bizQuote.claimRecord
			for(Input input : mq.inputs){
				if(mq.otherInfoBlock > 0){
				//年平均行驶里程
				if("bizQuote.kilometrePerYear".equals(input.name)) { 
					layoutBizQuoteKilometrePerYear = (LinearLayout)findViewById(R.id.info_plus_biz_quote_kilometre_per_year_layout);
					layoutBizQuoteKilometrePerYear.setVisibility(View.VISIBLE);
				//是否是过户车[非必须或未输入(null),是('1'),否('0')]
				}else if("bizQuote.specialCarFlag".equals(input.name)) {
					layoutBizQuoteSpecialCarFlag = (LinearLayout)findViewById(R.id.info_plus_biz_quote_special_car_flag_layout);
					layoutBizQuoteSpecialCarFlag.setVisibility(View.VISIBLE);
				//过户日期,如为null则表示为非过户车或报价不需要此项,格式:yyyy-MM-dd
				}else if("bizQuote.specialCarDate".equals(input.name)) {
					layoutBizQuoteSpecialCarDate = (LinearLayout)findViewById(R.id.info_plus_biz_quote_special_car_date_layout);
					layoutBizQuoteSpecialCarDate.setVisibility(View.VISIBLE);
				//用户是否能提供上年商业险保单[非必须或未输入(null),是('1'),否('0')]
				}else if("bizQuote.offerLastPolicy".equals(input.name)) {
					layoutBizQuoteOfferLastPolicy = (LinearLayout)findViewById(R.id.info_plus_biz_quote_offer_last_policy_layout);
					layoutBizQuoteOfferLastPolicy.setVisibility(View.VISIBLE);
				//用户输入的上年非平安承保的商业险险保单号(即转保保单号)
				}else if("bizQuote.lastPolicyNo".equals(input.name)) {
					layoutBizQuoteLastPolicyNo = (LinearLayout)findViewById(R.id.info_plus_biz_quote_last_policy_no_layout);
					layoutBizQuoteLastPolicyNo.setVisibility(View.VISIBLE);
				//商业险理赔记录
				}else if("bizQuote.claimRecord".equals(input.name)) {
					layoutBizQuoteClaimRecord = (LinearLayout)findViewById(R.id.info_plus_biz_quote_claim_record_layout);
					layoutBizQuoteClaimRecord.setVisibility(View.VISIBLE);
				}
			} else {
				layoutOtherInfoBlock= (LinearLayout)findViewById(R.id.info_plus_other_info_block_layout);
				layoutOtherInfoBlock.setVisibility(View.GONE);
			}
				
//				是否需要补充监管信息,0不需要,大于0需要 
//				   可能存在的录入项：
//				    上年交强险保费：forceQuote.lastPolicyPremium
//				    交强险理赔情况：forceQuote.claimRecord
//				    上年是否有酒驾记录：forceQuote.hasDrinkDriveRecord
//				    酒后驾驶(不含醉驾)次数： forceQuote.drinkDriveTimes
//				    醉驾次数：forceQuote.drunkDriveTimes
//				    上年交强险保单号：forceQuote.lastPolicyNo
//				    是否已缴纳上年车船税：taxForm.hasTax
//				    上年车船税缴纳方式：taxForm.taxApproach
//				    同时缴纳车船税的上年交强险保单号：taxForm.lastForcePolicyNo
//				 taxForm.payTaxNo：taxForm.payTaxNo
				if(mq.supervisorBlock > 0) {
					//上年交强险保费
					if("forceQuote.lastPolicyPremium".equals(input.name)) {
						layoutForceQuoteLastPolicyPremium = (LinearLayout)findViewById(R.id.info_plus_force_quote_last_policy_premium_layout);
						layoutForceQuoteLastPolicyPremium.setVisibility(View.VISIBLE);
					//交强险理赔情况
					} else if("forceQuote.claimRecord".equals(input.name)) {
						layoutForceQuoteClaimRecord = (LinearLayout)findViewById(R.id.info_plus_force_quote_claim_record_layout);
						layoutForceQuoteClaimRecord.setVisibility(View.VISIBLE);
					//上年是否有酒驾记录
					} else if("forceQuote.hasDrinkDriveRecord".equals(input.name)) {
						layoutForceQuoteHasDrinkDriveRecord = (LinearLayout)findViewById(R.id.info_plus_force_quote_has_drink_drive_record_layout);
						layoutForceQuoteHasDrinkDriveRecord.setVisibility(View.VISIBLE);
					//酒后驾驶(不含醉驾)次数
					} else if("forceQuote.drinkDriveTimes".equals(input.name)) {
						layoutForceQuoteDrinkDriveTimes = (LinearLayout)findViewById(R.id.info_plus_force_quote_drink_drive_times_layout);
						layoutForceQuoteDrinkDriveTimes.setVisibility(View.VISIBLE);
					//醉驾次数
					} else if("forceQuote.drunkDriveTimes".equals(input.name)) {
						layoutForceQuoteDrunkDriveTimes = (LinearLayout)findViewById(R.id.info_plus_force_quote_drunk_drive_times_layout);
						layoutForceQuoteDrunkDriveTimes.setVisibility(View.VISIBLE);
					//上年交强险保单号
					} else if("forceQuote.lastPolicyNo".equals(input.name)) {
						layoutForceQuoteLastPolicyNo = (LinearLayout)findViewById(R.id.info_plus_force_quote_last_policy_no_layout);
						layoutForceQuoteLastPolicyNo.setVisibility(View.VISIBLE);
					// 是否已缴纳上年车船税
					}else if("taxForm.hasTax".equals(input.name)) {
						layoutTaxFormHasTax = (LinearLayout)findViewById(R.id.info_plus_tax_form_has_tax_layout);
						layoutTaxFormHasTax.setVisibility(View.VISIBLE);
					//上年车船税缴纳方式	
					}else if("taxForm.taxApproach".equals(input.name)) {
						layoutTaxFormTaxApproach = (LinearLayout)findViewById(R.id.info_plus_tax_form_tax_approach_layout);
						layoutTaxFormTaxApproach.setVisibility(View.VISIBLE);
					//同时缴纳车船税的上年交强险保单号	
					}else if("taxForm.lastForcePolicyNo".equals(input.name)) {
						layoutTaxFormLastForcePolicyNo = (LinearLayout)findViewById(R.id.info_plus_tax_form_last_force_policyNo_layout);
						layoutTaxFormLastForcePolicyNo.setVisibility(View.VISIBLE);
					//taxForm.payTaxNo
					} else if("taxForm.payTaxNo".equals(input.name)) {
						layoutTaxFormPayTaxNo = (LinearLayout)findViewById(R.id.info_plus_tax_form_pay_tax_no_layout);
						layoutTaxFormPayTaxNo.setVisibility(View.VISIBLE);
					}  
					
				} else {
					layoutForceQuote = (LinearLayout)findViewById(R.id.info_plus_force_quote_layout);
					layoutForceQuote.setVisibility(View.GONE);
				}
		}
	}
	
}
