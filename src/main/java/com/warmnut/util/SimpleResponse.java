package com.warmnut.util;

import com.warmnut.enumerate.YgngError;

/**
 * 返回接口调用的错误码和信息
 * @author qinyao
 *
 */
public class SimpleResponse {
	
	private int errorCode;
	private String errorMsg;	
	
	public SimpleResponse() {
		super();
	}
	
	
	
	public SimpleResponse(YgngError error) {
		super();
		this.errorCode = error.value();
		this.errorMsg = error.getReasonPhrase();
	}



	public SimpleResponse(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
