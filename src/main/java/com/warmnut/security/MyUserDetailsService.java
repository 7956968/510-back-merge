package com.warmnut.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.warmnut.bean.Permission;
import com.warmnut.bean.User;
import com.warmnut.bean.User;
import com.warmnut.dao.PermissionMapper;
import com.warmnut.dao.UserMapper;
import com.warmnut.util.PermissionUtil;

/**
 * 表单登录，社交登录返回user对象服务
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserMapper adminRepository;

	
	@Autowired
	private PermissionMapper permission1Repository;

	@Override
	public UserDetails loadUserByUsername(String Name) throws UsernameNotFoundException {
		logger.info("表单登录用户名为:" + Name);
		User admin = adminRepository.selectByJobNumber(Name);
		
		if (admin != null && admin.getItemStatus()) {
			logger.info(admin.toString());
			List<Permission> permissions = permission1Repository.findByRoleId(admin.getRoleId());
			List<Map<String,Object>> a = PermissionUtil.getChild(0, permissions);
//			String b = JSON.toJSONString(a);
//			System.err.println(b);
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for (Permission permission : permissions) {
				GrantedAuthority grantedAuthority = null;
				grantedAuthority = new MyGrantedAuthority("", null,
						permission.getPath(),null);
				grantedAuthorities.add(grantedAuthority);
			}
			admin.setRouters(a);
			admin.setAuthorities(grantedAuthorities);
			return admin;
		} else {
			throw new UsernameNotFoundException("Name " + Name + " not found");
		}
	}

}
