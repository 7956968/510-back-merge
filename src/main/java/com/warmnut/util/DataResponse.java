package com.warmnut.util;

import com.warmnut.enumerate.YgngError;

/**
 * 公共响应类
 * @author qinyao
 *
 * @param <T>
 */

public class DataResponse<T> extends SimpleResponse{
	
	
	/**
	 * 返回的数据
	 */
	private T data;
	
	
	public DataResponse() {
		super();
	}
	public DataResponse(int errorCode, String errorMsg, T data) {
		super(errorCode,errorMsg);		
		this.data = data;
	}
	
	public DataResponse(YgngError error, T data) {
		super(error.value(),error.getReasonPhrase());		
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
