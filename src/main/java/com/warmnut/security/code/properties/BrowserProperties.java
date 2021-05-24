package com.warmnut.security.code.properties;

import com.warmnut.enumerate.LoginResponseType;

public class BrowserProperties {

    /**
     * session配置
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 登录页
     */
    private String loginPage = "/default-signIn.html";

    /**
     * 注册页
     */
    private String registerPage = "/default-signUp.html";

    /**
     * 登录响应类型
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     *
     * 只在signInResponseType为REDIRECT时生效
     */
    private String singInSuccessUrl;

    /**
     * 记住我时间秒数
     */
    private int rememberMeSeconds = 60*60*12;

    /**
     * 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
     */
    private String signOutUrl;
    
    /**允许免登陆访问的地址,使用逗号隔开*/
    private String permitAll;


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getRegisterPage() {
        return registerPage;
    }

    public void setRegisterPage(String registerPage) {
        this.registerPage = registerPage;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public String getSingInSuccessUrl() {
        return singInSuccessUrl;
    }

    public void setSingInSuccessUrl(String singInSuccessUrl) {
        this.singInSuccessUrl = singInSuccessUrl;
    }

	public String getPermitAll() {
		return permitAll;
	}

	public void setPermitAll(String permitAll) {
		this.permitAll = permitAll;
	}
    
}
