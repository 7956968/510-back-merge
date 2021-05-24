package com.warmnut.enumerate;

public enum ItemStatus {
	normal(1, "正常"),
	deleted(2, "已删除");
	Integer key;
	String value;	
	
	ItemStatus(Integer key, String value) {
		this.key = key;
		this.value = value;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
