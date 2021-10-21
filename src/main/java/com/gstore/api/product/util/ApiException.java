package com.gstore.api.product.util;

public class ApiException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	public ApiException(String message) {
		super(message);
	}
	
	public ApiException(int code, String message) {
		this.code = code;
		this.message=message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
