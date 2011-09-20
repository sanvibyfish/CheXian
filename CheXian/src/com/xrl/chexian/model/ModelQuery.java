package com.xrl.chexian.model;

import java.io.Serializable;
import java.util.List;

public class ModelQuery implements Result, Serializable{

	public int resultCode;
	public String name;
	public String flowid;
	public String supervisorBlock;
	public List<Model> models;
	public String shenZhenTaxBlock;
	public List<Input> inputs;
	public TaxPrompt taxPrompt;
	public String otherInfoBlock;
	
	
}
