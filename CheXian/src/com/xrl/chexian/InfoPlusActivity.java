package com.xrl.chexian;

import java.util.HashMap;

import com.xrl.chexian.adapter.RecordAdapter;
import com.xrl.chexian.adapter.CityAdapter;
import com.xrl.chexian.model.City;
import com.xrl.chexian.model.Input;
import com.xrl.chexian.model.Model;
import com.xrl.chexian.model.ModelQuery;
import com.xrl.chexian.model.Option;
import com.xrl.chexian.utils.ActivityUtils;
import com.xrl.chexian.utils.SharedPreferencesUtils;
import com.xrl.chexian.utils.gson.GsonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InfoPlusActivity extends Activity{

	//控件声明
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
	private RadioGroup radioGroupIsCarOwn;
	private TextView textViewTitle;
	private ImageView imgTitleProgress;
	private Button btnBack;
	private Button btnNext;
	private RadioButton radioIsCarOwnYes;
	private RadioButton radioIsCarOwnNo;
	private EditText editTextBizQuoteSpecialCarDate;
	private RadioGroup radioGroupBizQuoteOfferLastPolicy;
	private RadioButton radioBizQuoteOfferLastPolicyNo;
	private RadioButton radioBizQuoteOfferLastPolicyYes;
	private EditText editTextBizQuoteLastPolicyNo;

	
	//静态变量声明
	private static final String  BIZ_QUOTE_KILOMETRE_PER_YEAR= "bizQuote.kilometrePerYear";
	private static final String BIZ_QUOTE_SPECIAL_CAR_FLAG = "bizQuote.specialCarFlag";
	private static final String BIZ_QUOTE_SPECIAL_CAR_DATE = "bizQuote.specialCarDate";
	private static final String BIZ_QUOTE_OFFER_LAST_POLICY = "bizQuote.offerLastPolicy";
	private static final String BIZ_QUOTE_LAST_POLOCY_NO = "bizQuote.lastPolicyNo";
	private static final String BIZ_QUOTE_CLAIM_RECORD= "bizQuote.claimRecord";
	private static final String FORCE_QUOTE_LAST_POLICY_PREMIUM = "forceQuote.lastPolicyPremium";
	private static final String FORCE_QUOTE_CLAIM_RECORD = "forceQuote.claimRecord";
	private static final String FORCE_QUOTE_HAS_DRINK_DRIVE_RECORD = "forceQuote.hasDrinkDriveRecord";
	private static final String FORCE_QUOTE_DRINK_DRIVE_TIMES = "forceQuote.drinkDriveTimes";
	private static final String FORCE_QUOTE_DRUNK_DRIVE_TIMES = "forceQuote.drunkDriveTimes";
	private static final String FORCE_QUOTE_LAST_POLICY_NO = "forceQuote.lastPolicyNo";
	private static final String TAX_FORM_HAS_TAX = "taxForm.hasTax";
	private static final String TAX_FORM_TAX_APPROACH = "taxForm.taxApproach";
	private static final String TAX_FORM_LAST_FORCE_POLICY_NO = "taxForm.lastForcePolicyNo";
	private static final String TAX_FORM_PAY_TAX_NO = "taxForm.payTaxNo";
	
	HashMap<String, Input> inputHashMap = new HashMap<String, Input>();
	private RecordAdapter bizQuoteClaimRecordAdapter;
	private Option currentBizQuoteClaimRecordOption;
	private EditText editTextDorceQuoteLastPolicyPremium;
	private RecordAdapter forceQuoteClaimRecordAdapter;
	private Option currentForceQuoteClaimRecordOption;
	private RadioGroup radioGroupForceQuoteHasDrinkDriveRecord;
	private RadioButton radioForceQuoteHasDrinkDriveRecordYes;
	private RadioButton radioForceQuoteHasDrinkDriveRecordNo;
	private EditText editTextForceQuoteLastPolicyNo;
	private RadioGroup radioGroupTaxFormHasTax;
	private RadioButton radioTaxFormHasTaxYes;
	private RadioButton radioTaxFormHasTaxNo;
	private EditText editTextTaxFormPayTaxNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_plus);
		initComponent();
	}

	private void fillBundle(Bundle bundle) {
		if(layoutBizQuoteKilometrePerYear != null){
			bundle.putString(BIZ_QUOTE_KILOMETRE_PER_YEAR, editTextBizQuoteKilometrePerYear.getText().toString());
		}
		
		if(layoutBizQuoteSpecialCarFlag != null) {
			if(radioIsCarOwnYes.isChecked()) {
				bundle.putInt(BIZ_QUOTE_SPECIAL_CAR_FLAG, 1);
			} else if(radioIsCarOwnNo.isChecked()){
				bundle.putInt(BIZ_QUOTE_SPECIAL_CAR_FLAG, 0);
			}
		}
		
		if(editTextBizQuoteSpecialCarDate != null && radioIsCarOwnYes.isChecked()) {
			bundle.putString(BIZ_QUOTE_SPECIAL_CAR_DATE, editTextBizQuoteSpecialCarDate.getText().toString());
		}
		
		if(layoutBizQuoteOfferLastPolicy != null) {
			if(radioBizQuoteOfferLastPolicyYes.isChecked()) {
				bundle.putInt(BIZ_QUOTE_OFFER_LAST_POLICY, 1);
			} else if(radioBizQuoteOfferLastPolicyNo.isChecked()){
				bundle.putInt(BIZ_QUOTE_OFFER_LAST_POLICY, 0);
			}
		}
		
		if(editTextBizQuoteLastPolicyNo != null && radioBizQuoteOfferLastPolicyYes.isChecked()) {
			bundle.putString(BIZ_QUOTE_LAST_POLOCY_NO, editTextBizQuoteLastPolicyNo.getText().toString());
		}
		
		if(layoutBizQuoteClaimRecord != null && currentBizQuoteClaimRecordOption != null) {
			bundle.putSerializable(BIZ_QUOTE_CLAIM_RECORD, currentBizQuoteClaimRecordOption);
		}
		
		if(layoutForceQuoteLastPolicyPremium != null ) {
			bundle.putString(FORCE_QUOTE_LAST_POLICY_PREMIUM, editTextDorceQuoteLastPolicyPremium.getText().toString());
		}
		
		if(layoutForceQuoteHasDrinkDriveRecord != null){
			if(radioForceQuoteHasDrinkDriveRecordYes.isChecked()) {
				bundle.putInt(FORCE_QUOTE_HAS_DRINK_DRIVE_RECORD, 1);
			} else if(radioForceQuoteHasDrinkDriveRecordNo.isChecked()){
				bundle.putInt(FORCE_QUOTE_HAS_DRINK_DRIVE_RECORD, 0);
			}
		}
		
		if(layoutForceQuoteLastPolicyNo != null){
			bundle.putString(FORCE_QUOTE_LAST_POLICY_NO, editTextForceQuoteLastPolicyNo.getText().toString());
		}
		
		if(layoutTaxFormPayTaxNo != null){
			bundle.putString(TAX_FORM_PAY_TAX_NO, editTextTaxFormPayTaxNo.getText().toString());
		}
	}
	
	
	private void initComponent() {
		Bundle bundle = getIntent().getExtras();
		final ModelQuery mq = (ModelQuery) bundle
				.getSerializable(ModelQueryActivity.MODEL_QUERY);	
		Model model  = (Model) bundle.getSerializable(ModelQueryModelsActivity.SELECTED_MODEL);
		System.out.println(model.brandName);
		//过户日期,如为null则表示为非过户车或报价不需要此项,格式:yyyy-MM-dd
		layoutBizQuoteSpecialCarDate = (LinearLayout)findViewById(R.id.info_plus_biz_quote_special_car_date_layout);
		textViewTitle = (TextView) findViewById(R.id.title_text_view);
		textViewTitle.setText("监管信息");
		imgTitleProgress = (ImageView) findViewById(R.id.title_progress);
		imgTitleProgress.setImageResource(R.drawable.progress_2);
		btnBack = (Button) findViewById(R.id.footer_back_button);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityUtils.back(InfoPlusActivity.this, getIntent());
			}
		});
		
		btnNext = (Button)findViewById(R.id.footer_next_button);
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityUtils.jump(InfoPlusActivity.this, SaveApplyInfoActivity.class, ActivityUtils.SAVE_APPLY_INFO);
			}
		});
		
		
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
				if(BIZ_QUOTE_KILOMETRE_PER_YEAR.equals(input.name)) { 
					
					inputHashMap.put(BIZ_QUOTE_KILOMETRE_PER_YEAR, input);
					
					layoutBizQuoteKilometrePerYear = (LinearLayout)findViewById(R.id.info_plus_biz_quote_kilometre_per_year_layout);
					layoutBizQuoteKilometrePerYear.setVisibility(View.VISIBLE);
					editTextBizQuoteKilometrePerYear = (EditText) findViewById(R.id.info_plus_biz_quote_kilometre_per_year_edit_text);
				//是否是过户车[非必须或未输入(null),是('1'),否('0')]
				}else if(BIZ_QUOTE_SPECIAL_CAR_FLAG.equals(input.name)) {
					
					inputHashMap.put(BIZ_QUOTE_SPECIAL_CAR_FLAG, input);
					
					layoutBizQuoteSpecialCarFlag = (LinearLayout)findViewById(R.id.info_plus_biz_quote_special_car_flag_layout);
					layoutBizQuoteSpecialCarFlag.setVisibility(View.VISIBLE);
					radioIsCarOwnYes = (RadioButton) findViewById(R.id.info_plus_radio_is_car_own_yes);
					radioIsCarOwnNo = (RadioButton) findViewById(R.id.info_plus_radio_is_car_own_no);
					
					editTextBizQuoteSpecialCarDate = (EditText) findViewById(R.id.info_plus_biz_quote_special_car_date_edit_text);
					radioGroupIsCarOwn = (RadioGroup)findViewById(R.id.info_plus_radio_is_car_own_group);
					radioGroupIsCarOwn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
							if(checkedId == R.id.info_plus_radio_is_car_own_yes){
								layoutBizQuoteSpecialCarDate.setVisibility(View.VISIBLE);
							} else {
								layoutBizQuoteSpecialCarDate.setVisibility(View.GONE);
							}
						}
						
					});
					
				//用户是否能提供上年商业险保单[非必须或未输入(null),是('1'),否('0')]
				}else if(BIZ_QUOTE_OFFER_LAST_POLICY.equals(input.name)) {
					
					inputHashMap.put(BIZ_QUOTE_OFFER_LAST_POLICY, input);
					
					layoutBizQuoteOfferLastPolicy = (LinearLayout)findViewById(R.id.info_plus_biz_quote_offer_last_policy_layout);
					radioGroupBizQuoteOfferLastPolicy = (RadioGroup)findViewById(R.id.radio_info_plus_biz_quote_offer_last_policy_group);
					radioBizQuoteOfferLastPolicyNo = (RadioButton)findViewById(R.id.radio_info_plus_biz_quote_offer_last_policy_no);
					radioBizQuoteOfferLastPolicyYes =(RadioButton)findViewById(R.id.radio_info_plus_biz_quote_offer_last_policy_yes);
					layoutBizQuoteLastPolicyNo = (LinearLayout)findViewById(R.id.info_plus_biz_quote_last_policy_no_layout);
					editTextBizQuoteLastPolicyNo = (EditText)findViewById(R.id.info_plus_biz_quote_last_policy_no_edit_text);
					radioGroupBizQuoteOfferLastPolicy.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId) {
							//用户输入的上年非平安承保的商业险险保单号(即转保保单号)
							if(checkedId == R.id.radio_info_plus_biz_quote_offer_last_policy_yes){
								layoutBizQuoteLastPolicyNo.setVisibility(View.VISIBLE);
							}else{
								layoutBizQuoteLastPolicyNo.setVisibility(View.GONE);
							}
						}
					});
					
				//商业险理赔记录
				}else if(BIZ_QUOTE_CLAIM_RECORD.equals(input.name)) {
					
					inputHashMap.put(BIZ_QUOTE_CLAIM_RECORD, input);
					
					layoutBizQuoteClaimRecord = (LinearLayout)findViewById(R.id.info_plus_biz_quote_claim_record_layout);
					layoutBizQuoteClaimRecord.setVisibility(View.VISIBLE);
					bizQuoteClaimRecordAdapter = new RecordAdapter(this, input.options);
					final AlertDialog.Builder ad = new AlertDialog.Builder(InfoPlusActivity.this);
					layoutBizQuoteClaimRecord.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ad.setSingleChoiceItems(bizQuoteClaimRecordAdapter, -1,  new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog, int which) {
									// a choice has been made!
									currentBizQuoteClaimRecordOption = (Option) bizQuoteClaimRecordAdapter.getItem(which);
									dialog.dismiss();
								}
							});
							ad.show();
						}
					});
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
					if(FORCE_QUOTE_LAST_POLICY_PREMIUM.equals(input.name)) {
						editTextDorceQuoteLastPolicyPremium = (EditText) findViewById(R.id.info_plus_force_quote_last_policy_premium_edit_text);
						inputHashMap.put(FORCE_QUOTE_LAST_POLICY_PREMIUM, input);
						
						layoutForceQuoteLastPolicyPremium = (LinearLayout)findViewById(R.id.info_plus_force_quote_last_policy_premium_layout);
						layoutForceQuoteLastPolicyPremium.setVisibility(View.VISIBLE);
					//交强险理赔情况
					} else if(FORCE_QUOTE_CLAIM_RECORD.equals(input.name)) {
						
						inputHashMap.put(FORCE_QUOTE_CLAIM_RECORD, input);
						final AlertDialog.Builder ad = new AlertDialog.Builder(InfoPlusActivity.this);
						forceQuoteClaimRecordAdapter = new RecordAdapter(this, input.options);
						layoutForceQuoteClaimRecord = (LinearLayout)findViewById(R.id.info_plus_force_quote_claim_record_layout);
						layoutForceQuoteClaimRecord.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								ad.setSingleChoiceItems(forceQuoteClaimRecordAdapter, -1,  new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										// a choice has been made!
										currentForceQuoteClaimRecordOption = (Option) bizQuoteClaimRecordAdapter.getItem(which);
										dialog.dismiss();
									}
								});
								ad.show();
							}
						});
						layoutForceQuoteClaimRecord.setVisibility(View.VISIBLE);
					//上年是否有酒驾记录
					} else if(FORCE_QUOTE_HAS_DRINK_DRIVE_RECORD.equals(input.name)) {
						
						inputHashMap.put(FORCE_QUOTE_HAS_DRINK_DRIVE_RECORD, input);
						layoutForceQuoteDrinkDriveTimes = (LinearLayout)findViewById(R.id.info_plus_force_quote_drink_drive_times_layout);
						layoutForceQuoteHasDrinkDriveRecord = (LinearLayout)findViewById(R.id.info_plus_force_quote_has_drink_drive_record_layout);
						layoutForceQuoteHasDrinkDriveRecord.setVisibility(View.VISIBLE);
						layoutForceQuoteDrunkDriveTimes = (LinearLayout)findViewById(R.id.info_plus_force_quote_drunk_drive_times_layout);
						radioGroupForceQuoteHasDrinkDriveRecord = (RadioGroup) findViewById(R.id.info_plus_force_quote_has_drink_drive_record_group);
						radioForceQuoteHasDrinkDriveRecordYes = (RadioButton) findViewById(R.id.info_plus_force_quote_has_drink_drive_record_yes);
						radioForceQuoteHasDrinkDriveRecordNo = (RadioButton) findViewById(R.id.info_plus_force_quote_has_drink_drive_record_no);
						radioGroupForceQuoteHasDrinkDriveRecord.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(RadioGroup group, int checkedId) {
								//用户输入的上年非平安承保的商业险险保单号(即转保保单号)
								if(checkedId == R.id.info_plus_force_quote_has_drink_drive_record_yes){
									layoutForceQuoteDrinkDriveTimes.setVisibility(View.VISIBLE);
									layoutForceQuoteDrunkDriveTimes.setVisibility(View.VISIBLE);
								}else{
									layoutForceQuoteDrinkDriveTimes.setVisibility(View.GONE);
									layoutForceQuoteDrunkDriveTimes.setVisibility(View.GONE);
								}
							}
						});
						
					//酒后驾驶(不含醉驾)次数
					} else if(FORCE_QUOTE_DRINK_DRIVE_TIMES.equals(input.name)) {
						inputHashMap.put(FORCE_QUOTE_DRINK_DRIVE_TIMES, input);
					//醉驾次数
					} else if(FORCE_QUOTE_DRUNK_DRIVE_TIMES.equals(input.name)) {
						inputHashMap.put(FORCE_QUOTE_DRUNK_DRIVE_TIMES, input);
					//上年交强险保单号
					} else if(FORCE_QUOTE_LAST_POLICY_NO.equals(input.name)) {

						inputHashMap.put(FORCE_QUOTE_LAST_POLICY_NO, input);
						
						layoutForceQuoteLastPolicyNo = (LinearLayout)findViewById(R.id.info_plus_force_quote_last_policy_no_layout);
						editTextForceQuoteLastPolicyNo = (EditText) findViewById(R.id.info_plus_force_quote_last_policy_no_edit_text);
						layoutForceQuoteLastPolicyNo.setVisibility(View.VISIBLE);
					// 是否已缴纳上年车船税
					}else if(TAX_FORM_HAS_TAX.equals(input.name)) {
						
						inputHashMap.put(TAX_FORM_HAS_TAX, input);
						layoutTaxFormHasTax = (LinearLayout)findViewById(R.id.info_plus_tax_form_has_tax_layout);
						layoutTaxFormHasTax.setVisibility(View.VISIBLE);
						radioGroupTaxFormHasTax = (RadioGroup) findViewById(R.id.info_plus_tax_form_has_tax_group);
						radioTaxFormHasTaxYes = (RadioButton) findViewById(R.id.info_plus_tax_form_has_tax_yes);
						radioTaxFormHasTaxNo = (RadioButton) findViewById(R.id.info_plus_tax_form_has_tax_no);
						
						layoutTaxFormTaxApproach = (LinearLayout)findViewById(R.id.info_plus_tax_form_tax_approach_layout);
						
						layoutTaxFormLastForcePolicyNo = (LinearLayout)findViewById(R.id.info_plus_tax_form_last_force_policyNo_layout);
						
						
						
						radioGroupTaxFormHasTax.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(RadioGroup group, int checkedId) {
								if(checkedId == R.id.info_plus_tax_form_has_tax_yes){
									layoutTaxFormTaxApproach.setVisibility(View.VISIBLE);
									layoutTaxFormLastForcePolicyNo.setVisibility(View.VISIBLE);
								} else {
									layoutTaxFormTaxApproach.setVisibility(View.GONE);
									layoutTaxFormLastForcePolicyNo.setVisibility(View.GONE);
								}
							}
						});
						
					//上年车船税缴纳方式	
					}else if(TAX_FORM_TAX_APPROACH.equals(input.name)) {
						inputHashMap.put(TAX_FORM_TAX_APPROACH, input);
					//同时缴纳车船税的上年交强险保单号	
					}else if(TAX_FORM_LAST_FORCE_POLICY_NO.equals(input.name)) {
						inputHashMap.put(TAX_FORM_LAST_FORCE_POLICY_NO, input);
					//taxForm.payTaxNo
					} else if(TAX_FORM_PAY_TAX_NO.equals(input.name)) {
						
						inputHashMap.put(TAX_FORM_PAY_TAX_NO, input);
						
						layoutTaxFormPayTaxNo = (LinearLayout)findViewById(R.id.info_plus_tax_form_pay_tax_no_layout);
						editTextTaxFormPayTaxNo = (EditText) findViewById(R.id.info_plus_tax_form_pay_tax_no_edit_text);
						
						layoutTaxFormPayTaxNo.setVisibility(View.VISIBLE);
					}  
					
				} else {
					layoutForceQuote = (LinearLayout)findViewById(R.id.info_plus_force_quote_layout);
					layoutForceQuote.setVisibility(View.GONE);
				}
		}
	}
	
}
