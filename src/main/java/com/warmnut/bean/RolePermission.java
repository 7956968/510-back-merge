package com.warmnut.bean;

/**
 * @author  lupincheng
 * @version  创建时间：2020/12/4 11:05
 * 角色与权限的关系
 */

public class RolePermission {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;

    public RolePermission() {
    }

    public RolePermission(Integer id, Integer roleId, Integer permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
