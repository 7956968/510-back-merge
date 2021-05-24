package com.warmnut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warmnut.bean.Dict;
import com.warmnut.bean.Permission;
import com.warmnut.bean.RolePermission;
import com.warmnut.dao.PermissionMapper;
import com.warmnut.dao.RolePermissionMapper;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.PermissionService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-31 19:47:49
   * 说明
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	
	@Autowired
	private PermissionMapper permissionDao;
	
	@Autowired
	private RolePermissionMapper rolePermissionDao;
	
	@Override
	public DataResponse<List<Permission>> selectAll(PageRequest param) {
		List<Permission> list = permissionDao.findAll();
		return new DataResponse<List<Permission>>(YgngError.SUCCESS.value(),"查询成功",list);
	}

	@Override
	public SimpleResponse add(Permission param) {
		SimpleResponse res = new SimpleResponse();
		try {
			Permission permission = permissionDao.selectByPath(param.getPath());
			if(permission != null) {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg("编码重复");
				return res;
			}
			int i = permissionDao.insertSelective(param);
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 添加权限，并为创建者对应的角色role赋权
	 * @param pms 权限实体 
	 * @param roleId 角色id
	 * @return
	 */
	@Override
	public SimpleResponse add(Permission pms, Integer roleId) {
		SimpleResponse res = new SimpleResponse();
		try {
			Permission permission = permissionDao.selectByPath(pms.getPath());
			if(permission != null) {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg("编码重复");
				return res;
			}
			int i = permissionDao.insertSelective(pms);
			int j = 0;
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
				// 为创建者对应的角色赋权
				j = rolePermissionDao.insertSelective(new RolePermission(null,roleId,pms.getId()));
				if(j > 0) { // 插入rolePermission成功
					
				}else {
					res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
					res.setErrorMsg("创建权限成功，但是为当前角色赋予权限失败");
				}
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public SimpleResponse updateById(Permission param) {
		SimpleResponse res = new SimpleResponse();
		try {
			Permission pms = permissionDao.selectById(param.getId());
			if(pms.getPid() != null)
				param.setPid(pms.getPid());
			int i = permissionDao.updateByPrimaryKeySelective(param);
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public SimpleResponse deleteById(int id) {
		SimpleResponse res = new SimpleResponse();
		try {
			int i = permissionDao.deleteById(id);
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 获取最后添加的权限
	 * @return 
	 */
	@Override
	public DataResponse<Permission> selectLast() {
		Permission pms = permissionDao.selectLast();
        return new DataResponse<Permission>(YgngError.SUCCESS.value(), "查询成功",pms);
	}

}
