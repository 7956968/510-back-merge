package com.warmnut.enumerate;

/**
 * 错误枚举
 * @author qinyao
 *
 */
public enum YgngError {
	SUCCESS(200, "操作成功"),
	PARAM_ERROR(4001, "参数错误"),
	UNKNOWN_ERROR(500, "服务器错误"),
	NO_DATA(5001, "没有数据"),	
	DUPLICATE_ENTRY(5003,"记录已存在,请勿重复添加");

	private final int value;

	private final String reasonPhrase;


	YgngError(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	

}
