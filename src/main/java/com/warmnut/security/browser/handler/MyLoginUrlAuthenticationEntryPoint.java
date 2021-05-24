package com.warmnut.security.browser.handler;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	private static final Log logger = LogFactory
			.getLog(MyLoginUrlAuthenticationEntryPoint.class);

	public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	
	}

	//覆盖父类的方法，将转发改为重定向
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		
		String loginForm = determineUrlToUseForThisRequest(request, response,
				authException);

		if (logger.isDebugEnabled()) {
			logger.debug("Server side forward to: " + loginForm);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);

		dispatcher.forward(request, response);//


	}
	
	

	


	

}