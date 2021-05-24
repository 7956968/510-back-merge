package com.warmnut.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.warmnut.bean.Permission;
import com.warmnut.service.PermissionService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-31 19:50:48
   * 说明
 */
@RestController
@RequestMapping("/permission/")
public class PermissionController {

	
	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("select")
	public DataResponse<List<Permission>> selectAll(PageRequest param){
		return permissionService.selectAll(param);
	}
	
	@GetMapping("select_last")
    public DataResponse<Permission> selectLast(){
        return permissionService.selectLast();
    }
	
//	@PostMapping("add")
//	public SimpleResponse add(Permission param){
//		return permissionService.add(param);
//	}
	
	/**
	 * 添加权限，并为创建权限的角色赋权
	 * @param data
	 * @return
	 */
	@PostMapping("add")
	public SimpleResponse add(@RequestBody HashMap<String,Object> data){
		Integer roleId = (Integer) data.get("roleId");
		Permission pms = JSON.parseObject(
				JSONObject.toJSONString(data.get("permission"),true),
				Permission.class
		);
		return permissionService.add(pms,roleId);
	}
	
	@PostMapping("updateById")
	public SimpleResponse updateById(Permission param){
		return permissionService.updateById(param);
	}
	
	@GetMapping("deleteById")
	public SimpleResponse deleteById(int id){
		return permissionService.deleteById(id);
	}
}
