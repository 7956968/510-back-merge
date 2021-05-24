package com.warmnut.security.browser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.warmnut.security.browser.handler.MyAuthenticationSuccessHandler;
import com.warmnut.security.browser.handler.MyLogoutSuccessHandler;
import com.warmnut.security.browser.session.MyExpiredSessionStrategy;
import com.warmnut.security.browser.session.MyInvalidSessionStrategy;
import com.warmnut.security.code.properties.SecurityProperties;
import com.warmnut.util.SpringContextHolder;

/**
 * session相关的session配置bean
 *
 * 可以通过demo项目实现接口覆盖
 *
 * 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new MyInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new MyExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 退出时的处理策略配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new MyLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }
    
    @Bean
    @ConditionalOnMissingBean(SavedRequestAwareAuthenticationSuccessHandler.class)
    public SavedRequestAwareAuthenticationSuccessHandler SavedRequestAwareAuthenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }
    
  
   
}