package com.warmnut.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warmnut.enumerate.YgngError;
import com.warmnut.util.DataResponse;


/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-23 15:45:09
   * 说明 用户控制层
 */
@RestController
@RequestMapping("user")
public class UserController {

	
	@GetMapping("/me")
	public DataResponse<Authentication> getMe() {
		Authentication authentication = SecurityContextHolder.getContext()
    			.getAuthentication(); 
		return new DataResponse<Authentication>(YgngError.SUCCESS,authentication);
	}
}
