package com.warmnut.service;

import com.warmnut.bean.Permission;
import com.warmnut.bean.RolePermission;
import com.warmnut.util.DataResponse;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/4 11:12
 * 角色-权限 关系 服务层
 */
public interface RolePermissionService {

    SimpleResponse add(RolePermission dept);

    SimpleResponse deleteById(Integer id);

    SimpleResponse deleteByRoleId(Integer roleId);

    // (暂时不使用)
    // SimpleResponse deleteAll(Integer roleId, ArrayList<Integer> pmsIdList);

    DataResponse<List<Permission>> selectByRoleId(Integer roleId);

    DataResponse<List<Integer>> selectPmsIdByRoleId(Integer roleId);

    SimpleResponse addAll(Integer roleId, ArrayList<Integer> pmsIdList);
}
