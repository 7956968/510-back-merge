package com.warmnut.service;
/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-31 19:45:54
   * 说明
 */

import java.util.List;

import com.warmnut.bean.Permission;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

public interface PermissionService {
	
	
	DataResponse<List<Permission>> selectAll(PageRequest param);
	
	SimpleResponse add(Permission param);
	
	/**
	 * 添加权限，并为创建者对应的角色role赋权
	 * @param pms 权限实体 
	 * @param roleId 角色id
	 * @return
	 */
	SimpleResponse add(Permission pms, Integer roleId);
	
	SimpleResponse updateById(Permission param);
	
	SimpleResponse deleteById(int id);

	DataResponse<Permission> selectLast();
}
