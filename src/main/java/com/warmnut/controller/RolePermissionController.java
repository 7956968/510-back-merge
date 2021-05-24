package com.warmnut.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.warmnut.bean.Permission;
import com.warmnut.bean.RolePermission;
import com.warmnut.service.RolePermissionService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * @author  lupincheng
 * @version  创建时间：2020/12/4 11:08
 * 角色-权限 关系 控制层
 */
@RestController
@RequestMapping("/role_permission/")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 添加操作权限（暂时不用）
     * @param rolePermission
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(RolePermission rolePermission){
        return rolePermissionService.add(rolePermission);
    }

    /**
     * 删除操作权限（暂时不用）
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return rolePermissionService.deleteById(id);
    }

    /**
     * 删除roleid的所有权限
     * @param params
     * @return
     */
    @PostMapping("delete_by_role_id")
    public SimpleResponse deleteByRoleId(@RequestBody HashMap<String,Object> params){
        Integer roleId = (Integer) (params.get("roleId"));
        return rolePermissionService.deleteByRoleId(roleId);
    }

    /**
     * 通过roleId和permissionList删除RP
     * @param params
     *      roleId: 角色id
     *      pmsIdList: 权限id列表
     * @return
     */
    // @PostMapping("delete_all")
    // public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> params){
    //     Integer roleId = (Integer) (params.get("roleId"));
    //     ArrayList<Integer> pmsIdList =  ((ArrayList<Integer>)(params.get("pmsIdList")));
    //     return rolePermissionService.deleteAll(roleId, pmsIdList);
    // }

    /**
     * 查询角色的所有权限
     * @param roleId 角色id
     * @return
     */
    @GetMapping("select_by_role_id")
    public DataResponse<List<Permission>> selectByRoleId(Integer roleId){
        return rolePermissionService.selectByRoleId(roleId);
    }

    /**
     * 查询某角色的所有权限的id列表
     * @param roleId
     * @return
     */
    @GetMapping("select_pmsId_by_role_id")
    public DataResponse<List<Integer>> selectPmsIdByRoleId(Integer roleId){
        return rolePermissionService.selectPmsIdByRoleId(roleId);
    }

    /**
     * 为角色添加权限，具体流程是：删除所有已有权限->添加所有权限
     * @param params
     *          roleId: 角色id
     *          pmsIdList: 权限id列表
     * @return
     */
    @PostMapping("add_all")
    public SimpleResponse addAll(@RequestBody HashMap<String,Object> params){
    	Integer roleId = (Integer) (params.get("roleId"));
    	ArrayList<Integer> pmsIdList =  ((ArrayList<Integer>)(params.get("pmsIdList")));
        return rolePermissionService.addAll(roleId, pmsIdList);
    }
}
