package com.warmnut.service;

import com.warmnut.bean.Role;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/1 18:15
 * 角色 服务层
 */
public interface RoleService {

    /**
     * 添加角色
     * @param role
     * @return
     */
    SimpleResponse add(Role role);

    /**
     * 添加角色，并且为角色赋予权限
     * @param role 角色
     * @param permissionIdList 权限的id列表
     * @return
     */
    SimpleResponse addWithPermissions(Role role, ArrayList<Integer> permissionIdList);

    /**
     * 删除角色
     * @param id
     * @return
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除角色
     * @param idList
     * @return
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 修改角色
     * @param role
     * @return
     */
    SimpleResponse updateById(Role role, ArrayList<Integer> permissionIdList);

    /**
     * 查询角色
     * @param params
     * @return
     */
    DataResponse<List<Role>> selectAll(PageRequest params);

    /**
     * 获取最近添加的角色
     * @return
     */
    DataResponse<Role> selectLast();

//    DataResponse<List<Role>> selectByName(String name);
}
