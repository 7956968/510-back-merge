package com.warmnut.bean;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 17:27
 * 用于包裹设备实例的"组"
 * 如果组没有父节点，则pid=0
 */

public class DeviceGroup {
    private Integer id;
    private String name; // 分组名
    private Integer pid; // 父组id

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer createUser; // 创建者id
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer updateUser; // 修改者id

    private ArrayList<Device> deviceList;   // 设备列表

    public DeviceGroup() {
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name==null?null:name.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
