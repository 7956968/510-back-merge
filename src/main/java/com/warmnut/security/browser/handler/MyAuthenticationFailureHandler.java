package com.warmnut.security.browser.handler;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warmnut.enumerate.LoginResponseType;
import com.warmnut.enumerate.YgngError;
import com.warmnut.security.code.properties.SecurityProperties;
import com.warmnut.util.SimpleResponse;



@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            String msg = exception.getMessage();
            if("Bad credentials".equals(exception.getMessage()) ) {
            	msg = "账号或密码错误";
            }
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(YgngError.UNKNOWN_ERROR.value(),"登录失败："+msg)));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
        
//        LogManager.me().executeLog(LogTaskFactory.loginLog(user.getId(), HttpKit.getIp(),LogSucceed.SUCCESS));
    }
}
