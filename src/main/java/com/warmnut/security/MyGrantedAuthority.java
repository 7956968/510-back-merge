package com.warmnut.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.warmnut.bean.S_menu;


public class MyGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7753344656395602435L;
	private String url;
	private String method;	
	private String authority;
	
	private List<S_menu> a;

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<S_menu> getA() {
		return a;
	}




	public void setA(List<S_menu> a) {
		this.a = a;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public MyGrantedAuthority(String url, String method, String authority) {
		super();
		this.url = url;
		this.method = method;		
		this.authority = authority;
	}



	public MyGrantedAuthority(String url, String method, String authority, List<S_menu> a) {
		super();
		this.url = url;
		this.method = method;
		this.authority = authority;
		this.a = a;
	}




	@Override
	public String getAuthority() {
		return this.authority;
	}
}
