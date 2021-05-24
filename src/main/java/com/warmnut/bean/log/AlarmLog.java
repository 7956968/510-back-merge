package com.warmnut.bean.log;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author lupincheng
 * @version  创建时间：2020/12/18 16:35
 * 报警日志
 */

public class AlarmLog {
    private Integer id;
    private String name;    // 报警主机（设备？）名
    private String location;    // 报警位置
    private String status;      // 状态：已处理/未处理
    private String description; // 描述
    private Integer handleId;   // 处理id
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date recordTime;    // 报警记录时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;     // 报警时间

    public AlarmLog(){}

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location==null?null:location.trim();;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status==null?null:status.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description==null?null:description.trim();
    }

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }
}
