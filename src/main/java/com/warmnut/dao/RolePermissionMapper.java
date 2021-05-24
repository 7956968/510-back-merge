package com.warmnut.dao;

import com.warmnut.bean.Permission;
import com.warmnut.bean.RolePermission;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/4 11:20
 */
@Mapper
public interface RolePermissionMapper {
    int insertSelective(RolePermission rp);

    int deleteById(Integer id);

    int deleteByRoleId(Integer roleId);

    int deleteByRoleIdList(ArrayList<Integer> roleIdList);

    // (暂时不使用)
    // int deleteAll(@Param(value="roleId") Integer roleId, @Param(value="pmsIdList") ArrayList<Integer> pmsIdList);

    RolePermission selectById(int id);

    List<Permission> selectByRoleId(Integer roleId);

    List<Integer> selectPmsIdByRoleId(Integer roleId);

    int insertAll(@Param(value="roleId") Integer roleId, @Param(value="pmsIdList") ArrayList<Integer> pmsIdList);
}
