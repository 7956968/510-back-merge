package com.warmnut.bean;

/**
 * @author lupincheng
 * @version 创建时间：2021/1/13 10:43
 * 包含设备与它的一个通道组成的实体
 * 考虑使用MyCamera类来取代这一个类
 */

public class DeviceStream extends Device{

    /** 通道名 */
    String channelName;
    /** 通道序列号 */
    String channelSerialNumber;
    /** 通道号 */
    Integer channelNumber;


    public DeviceStream() {
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName==null?"":channelName.trim();
    }

    public String getChannelSerialNumber() {
        return channelSerialNumber;
    }

    public void setChannelSerialNumber(String channelSerialNumber) {
        this.channelSerialNumber = channelSerialNumber==null?"":channelSerialNumber.trim();
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

}
