package com.warmnut.service.impl;

import com.warmnut.dao.RolePermissionMapper;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.log.LogManager;
import com.warmnut.log.LogTaskFactory;
import com.warmnut.util.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warmnut.bean.Role;
import com.warmnut.dao.RoleMapper;
import com.warmnut.service.RoleService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import com.warmnut.enumerate.YgngError;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/2 11:34
 * 角色 服务层实现
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleDao;
    @Autowired
    private RolePermissionMapper rolePmsDao;// role-permission的dao

    /**
     * 添加角色
     * @param role 角色
     * @return
     */
    @Override
    public SimpleResponse add(Role role) {
        SimpleResponse res = new SimpleResponse();
        try {
            int roleFound = roleDao.countByName(role.getName());
            if(roleFound > 0 ) {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg("角色名重复");
                return res;
            }
            int i = roleDao.insertSelective(role);
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
     * 添加角色，并添加权限
     * @param role 角色
     * @param permissionIdList 权限ID的列表
     * @return
     */
    @Override
    public SimpleResponse addWithPermissions(Role role, ArrayList<Integer> permissionIdList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int roleFound = roleDao.countByName(role.getName());
            if(roleFound > 0 ) {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg("角色名重复");
                return res;
            }
            int i = roleDao.insertSelective(role);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
            // 插入角色权限
            if( ! permissionIdList.isEmpty()){
                int j = rolePmsDao.insertAll(role.getId(), permissionIdList);
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = roleDao.deleteById(id);
            int j = rolePmsDao.deleteByRoleId(id);
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
    public SimpleResponse deleteAll(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = roleDao.deleteAll(idList);
            int j = rolePmsDao.deleteByRoleIdList(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("批量删除成功");
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public SimpleResponse updateById(Role role, ArrayList<Integer> permissionIdList) {
        SimpleResponse res = new SimpleResponse();
        try {
            // 修改角色名需要查重
            Role roleFound = roleDao.selectById(role.getId());
            if( ! roleFound.getName().equals(role.getName())){
                int nameFound = roleDao.countByName(role.getName());
                if(nameFound > 0 ) {
                    res.setErrorCode(YgngError.PARAM_ERROR.value());
                    res.setErrorMsg("角色名重复");
                    return res;
                }
            }
            role.setModifyTime(new Date());
            int i = roleDao.updateByPrimaryKeySelective(role);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg(YgngError.NO_DATA.getReasonPhrase());
            }
            // 先删除，再插入所有
            int j = rolePmsDao.deleteByRoleId(role.getId());
            if( ! permissionIdList.isEmpty()){
                int k = rolePmsDao.insertAll(role.getId(), permissionIdList);
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据关键字查找
     * @param params
     * @return
     */
    @Override
    public DataResponse<List<Role>> selectAll(PageRequest params) {
        List<Role> roleList = roleDao.selectAll(params);
        return new DataResponse<List<Role>>(YgngError.SUCCESS.value(), "查询成功",roleList);
    }

    /**
     * 查找最后添加的角色
     * @return
     */
    @Override
    public DataResponse<Role> selectLast() {
        Role role = roleDao.selectLast();
        return new DataResponse<Role>(YgngError.SUCCESS.value(), "查询成功",role);
    }


}
