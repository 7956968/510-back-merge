package com.warmnut.service.impl;


import com.warmnut.bean.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warmnut.bean.RolePermission;
import com.warmnut.dao.RolePermissionMapper;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.RolePermissionService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/4 11:16
 * 角色-权限 关系 服务层实现
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionDao;

    @Override
    public SimpleResponse add(RolePermission rolePermission) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = rolePermissionDao.insertSelective(rolePermission);
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
     * 为角色批量赋予权限
     * @param roleId 角色id
     * @param pmsIdList 权限id列表
     * @return
     */
    public SimpleResponse addAll(Integer roleId, ArrayList<Integer> pmsIdList) {
        SimpleResponse res = new SimpleResponse();
        try {
            if (roleId==null){
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
            int i = rolePermissionDao.insertAll(roleId, pmsIdList);
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
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = rolePermissionDao.deleteById(id);
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
    public SimpleResponse deleteByRoleId(Integer roleId) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = rolePermissionDao.deleteByRoleId(roleId);
            if(i >= 0) {
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

    // (暂时不使用)
    // public SimpleResponse deleteAll(Integer roleId, ArrayList<Integer> pmsIdList) {
    //     SimpleResponse res = new SimpleResponse();
    //     try {
    //         int i = rolePermissionDao.deleteAll(roleId, pmsIdList);
    //         if(i > 0) {
    //             res.setErrorCode(YgngError.SUCCESS.value());
    //             res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
    //         }else {
    //             res.setErrorCode(YgngError.PARAM_ERROR.value());
    //             res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
    //         }
    //     }catch(Exception e) {
    //         res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
    //         res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    @Override
    public DataResponse<List<Permission>> selectByRoleId(Integer roleId) {
        List<Permission> permissionsList = rolePermissionDao.selectByRoleId(roleId);
        return new DataResponse<List<Permission>>(YgngError.SUCCESS.value(), "查询成功", permissionsList);
    }

    @Override
    public DataResponse<List<Integer>> selectPmsIdByRoleId(Integer roleId) {
        List<Integer> pmsIdList = rolePermissionDao.selectPmsIdByRoleId(roleId);
        return new DataResponse<List<Integer>>(YgngError.SUCCESS.value(), "查询成功", pmsIdList);
    }
}
