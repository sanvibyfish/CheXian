package com.xrl.chexian.error;

/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-8-31 下午01:43:27
 */
public class CheXianException extends Exception {
	private static final long serialVersionUID = 1L;
	private String mExtra;

	public CheXianException(String message) {
		super(message);
	}

	public CheXianException(String message, String extra) {
		super(message);
		mExtra = extra;
	}

	public String getExtra() {
		return mExtra;
	}
}
