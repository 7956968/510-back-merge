package com.warmnut.bean;

import java.io.Serializable;

public class S_menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7015940911795914068L;
	private String authority;
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public S_menu(String authority) {
		super();
		this.authority = authority;
	}
	
}
