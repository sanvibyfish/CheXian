package com.xrl.chexian.model;

import java.util.List;

public class ModelQuery {

	public String name;
	public String flowid;
	public String supervisorBlock;
	public String resultCode;
	
	public List<Model> models;
	public String shenZhenTaxBlock;
	public List<Input> inputs;
	public TaxPrompt taxPrompt;
	public String otherInfoBlock;
}
