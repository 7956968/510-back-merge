package com.warmnut.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.warmnut.util.DataResponse;
import com.warmnut.util.MyTest;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warmnut.bean.Role;
import com.warmnut.service.RoleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/02 14:53
 * 角色-控制层
 */
@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(Role role){
        return roleService.add(role);
    }

    /**
     * 添加角色，同时添加权限
     * @param data { role: * , permissionIdList: *}
     *             role: 角色实体
     *             permissionIdList 权限的id列表
     * @return
     */
    @PostMapping("add_with_permissions")
    public SimpleResponse addWithPermissions(@RequestBody HashMap<String,Object> data){
        Role role = JSON.parseObject(JSONObject.toJSONString(data.get("role"),true),Role.class);
        ArrayList<Integer> permissionIdList = (ArrayList<Integer>) data.get("permissionIdList");
        return roleService.addWithPermissions(role, permissionIdList);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return roleService.deleteById(id);
    }

    /**
     * 批量删除角色
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return roleService.deleteAll(idList);
    }

    /**
     * 修改角色，包括角色的名字，描述，权限
     * @param data { role: * , permissionIdList: *}
     *             role: 角色实体
     *             permissionIdList 权限的id列表
     * @return
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(@RequestBody HashMap<String,Object> data){
        Role role = JSON.parseObject(JSONObject.toJSONString(data.get("role"),true),Role.class);
        ArrayList<Integer> permissionIdList = (ArrayList<Integer>) data.get("permissionIdList");
        return roleService.updateById(role, permissionIdList);
    }


    /**
     * 关键字查询角色
     * @param params
     * @return
     */
    @GetMapping("select")
    public DataResponse<List<Role>> selectAll(PageRequest params){
        return roleService.selectAll(params);
    }

    /**
     * 查询最后被添加的角色
     * @return
     */
    @GetMapping("select_last")
    public DataResponse<Role> selectLast(){
        return roleService.selectLast();
    }
}
