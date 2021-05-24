package com.warmnut.enumerate;

/**
 * 用户类型
 * @author zhaoxiaozhou
 *
 */
public enum PermissionType {
	MENU_TYPE("menuType","菜单类型"),
	MENU("menu","菜单"),
	PAGE("page","页面"),
	BUTTON("button","按钮");
	String key;
	String value;	
	PermissionType(String string, String value) {
		this.key = string;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
