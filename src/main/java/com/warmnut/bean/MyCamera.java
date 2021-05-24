package com.warmnut.bean;

import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2021/5/13 16:21
 * 带通道的摄像头设备，为了避免和别的包中的Camera类重名，命名为MyCamera
 */

public class MyCamera extends Device{
    List<Channel> channels;

    MyCamera(){

    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
