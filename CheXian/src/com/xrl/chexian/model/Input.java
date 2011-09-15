package com.xrl.chexian.model;

import java.io.Serializable;
import java.util.List;

public class Input implements Result, Serializable{

	public String name;
	public String value;
	public List<Option> options;
	public String hidden;
}
