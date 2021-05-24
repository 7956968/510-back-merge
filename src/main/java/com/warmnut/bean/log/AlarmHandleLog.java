package com.warmnut.bean.log;

import java.util.Date;

/**
 * @author  lupincheng
 * @version 创建时间：2020/12/18 16:42
 * 报警处理日志
 */

public class AlarmHandleLog {
    private Integer id;
    private Integer userId;     // 处理人ID
    private String type;        // 处理类型
    private String description; // 描述
    private Date createTime;    // 处理时间
    private String userName;    // 处理人姓名

    public AlarmHandleLog(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description==null?null:description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
