package com.warmnut.security.browser;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.warmnut.security.browser.handler.MyLoginUrlAuthenticationEntryPoint;
import com.warmnut.security.code.AuthorizeConfigManager;
import com.warmnut.security.code.FormAuthenticationConfig;
import com.warmnut.security.code.properties.SecurityConstants;
import com.warmnut.security.code.properties.SecurityProperties;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;



	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;

	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;

	@Autowired
	protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

	/**
	 * 记住我功能的token存取器配置
	 * 
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		// 系统在启动时会自动生成表
		// tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;

	}

	@Override /** 用户认证 */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// remember me
		auth.eraseCredentials(false);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 表单登录配置项
		formAuthenticationConfig.configure(http);

		http

				// // 引用社交配置
				// .apply(earthchenSocialConfig)
				// .and()
				.rememberMe().tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService).and()
				// session 配置
				.sessionManagement().invalidSessionStrategy(invalidSessionStrategy)
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
				.expiredSessionStrategy(sessionInformationExpiredStrategy)
				// 设置session失效之后跳转到的url
				// .invalidSessionUrl("/session/invalid")
				// // 设置最大session数量
				// .maximumSessions(1)
				// //当session数量达到最大时，阻止后来的用户登录
				// //.maxSessionsPreventsLogin(true)
				// // session超时处理策略
				// .expiredSessionStrategy(new ImoocExpiredSessionStrategy())
				.and().and().exceptionHandling().authenticationEntryPoint(myLoginUrlAuthenticationEntryPoint()).and()
				.logout().logoutUrl("/logout")
				// .logoutSuccessUrl("/imooc-logout.html")
				.logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID").and().authorizeRequests()
				// .antMatchers("/","/static/**", "/login", "/login/**","/code/**",
				// "/user/checkCode","/user/checkPhone","/user/register","/*.ico").permitAll()
				.antMatchers(securityProperties.getBrowser().getPermitAll().split(",")).permitAll()//
				.and().headers().frameOptions().sameOrigin().and().csrf().disable();

		authorizeConfigManager.config(http.authorizeRequests());

	}

	@Bean
	public MyLoginUrlAuthenticationEntryPoint myLoginUrlAuthenticationEntryPoint() {
		return new MyLoginUrlAuthenticationEntryPoint(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL);
	}

	// @Override
	// public void configure(WebSecurity web) throws Exception {
	// //解决静态资源被拦截的问题
	// web.ignoring().antMatchers("**.xlsx");
	// }

}
