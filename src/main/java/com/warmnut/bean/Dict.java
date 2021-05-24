package com.warmnut.bean;

import java.io.Serializable;
import java.util.Date;

import com.warmnut.util.PageRequest;

public class Dict extends PageRequest implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8973676980512307141L;

	private Integer id;

    private Integer pid;

    private String code;

    private String name;

    private Boolean isDelete;

    private Boolean isUpdate;

    private Date createTime;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}