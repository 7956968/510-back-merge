package com.warmnut.bean;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author  lupincheng
 * @version  创建时间：2020/12/8 10:45
 * 设备
 */
public class Device {
    private Integer id;
    private String manufacturers;   // 厂家
    private String name;
    private String serialNumber;// 序列号
    private String type;
    private String ip;
    private String port;        // 设备端口号
    private String loginName;
    private String password;
    /**
     * rtsp地址格式：
     *     "HIKVISION" 海康（旧）
     *     "HIKVISION_NEW" 海康（新）
     *     "DAHUA" 大华
     *     "DLINK" 友讯
     *     "AXIS" 安迅士
     *     待补充...
     */
    private String rtspFormat;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer createUser; // 创建者id
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer updateUser; // 修改者id

    private Integer groupId;    // 分组id

    public Device() {
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers==null?null:manufacturers.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name==null?null:name.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber==null?null:serialNumber.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type==null?null:type.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip==null?null:ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port ==null?null: port.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName==null?null:loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password==null?null:password.trim();
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getRtspFormat() {
        return rtspFormat;
    }

    public void setRtspFormat(String rtspFormat) {
        this.rtspFormat = rtspFormat;
    }
}
