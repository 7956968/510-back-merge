package com.warmnut.util;

import com.warmnut.bean.Channel;
import com.warmnut.bean.MyCamera;

import java.util.*;

/**
 * @author lupincheng
 * @version 创建时间：2021/4/1 16:26
 *
 * 地址格式参考的网址：
 *  https://blog.csdn.net/viola_lulu/article/details/53330727
 *  https://www.cnblogs.com/wanggang123/p/8442683.html
 */

public class RtspAddress {

    // 下述的各格式的字符串成员变量暂时不使用，只使用拼接方法concatXXX(camera)

    /**
     * 海康威视摄像头地址格式
     * username: 用户名. 例如admin.
     * password: 密码. 例如12345.
     * ip: 设备IP. 例如 192.0.0.64.
     * port: 端口号默认为554，若为默认可不填写.
     * codec：有h264、MPEG-4、mpeg4这几种.
     * channel: 通道号，起始为1. 例如通道1，则为ch1.
     * subtype: 码流类型，主码流为main，辅码流为sub.
     */
    public static final String HIKVISION_FORMAT =
            "rtsp://[username]:[password]@[ip]:[port]/[codec]/[channel]/[subtype]/av_stream";
    /**
     * 第2种海康威视摄像头地址格式
     * username: 用户名. 例如admin.
     * password: 密码. 例如12345.
     * ip: 设备IP. 例如 192.0.0.64.
     * port: 端口号默认为554，若为默认可不填写.
     * subtype: 码流类型，主码流为1，辅码流为2，第三码流为3.
     */
    public static final String HIKVISION_NEW_FORMAT =
            "rtsp://[username]:[password]@[ip]:[port]/Streaming/Channels/[subtype]";

    /**
     * 大华摄像头地址格式
     * username: 用户名。例如admin。
     * password: 密码. 例如12345
     * ip: 为设备IP。例如 10.7.8.122。
     * port: 端口号默认为554，若为默认可不填写。
     * channel: 通道号，起始为1。例如通道2，则为channel=2。
     * subtype: 码流类型，主码流为0（即subtype=0），辅码流为1（即subtype=1）
     */
    public static final String DAHUA_FORMAT =
            "rtsp://[username]:[password]@[ip]:[port]/cam/realmonitor?channel=[channel]&subtype=[subtype]";

    /**
     * 友讯摄像头地址格式
     * username：用户名。例如admin
     * password：密码。例如12345，如果没有网络验证可直接写成rtsp://[ip]:[port]/[channel].sdp
     * ip：为设备IP。例如192.168.0.108。
     * port：端口号默认为554，若为默认可不填写。
     * channel：通道号，起始为1。例如通道2，则为live2。
     */
    public static final String DLINK_FORMAT =
            "rtsp://[username]:[password]@[ip]:[port]/live[channel].sdp";

    /**
     * 安迅士摄像头地址格式
     * username：用户名。例如admin
     * password：密码。例如12345，如果没有网络验证可省略用户名密码部分以及@字符。
     * ip：为设备IP。例如192.168.0.108。
     * videocodec：支持MPEG、h264等，可缺省。
     * resolution：分辨率，如resolution=1920x1080，若采用默认分辨率，可缺省此参数。
     */
    public static final String AXIS_FORMAT =
            "rtsp://[username]:[password]@[ip]/axis-media/media.amp?videocodec=[videocodec]&resolution=[resolution]";

    /**
     * 拼接rtsp地址，摄像头品牌：海康威视
     * @param camera 带通道的设备实体
     * @return 视频流地址
     */
    public static ArrayList<String> concatHikVisionAddress(MyCamera camera){
        ArrayList<String> rtspList = new ArrayList<>();
        for(Channel channel: camera.getChannels()){
            StringBuilder rtspAddr = new StringBuilder();
            rtspAddr
                    .append("rtsp://")
                    .append(camera.getLoginName())
                    .append(":")
                    .append(camera.getPassword())
                    .append("@")
                    .append(camera.getIp())
                    .append(":")
                    .append(camera.getPort())
                    .append("/")
                    .append("h264")
                    .append("/ch")
                    .append(channel.getNumber())
                    .append("/")
                    .append("main")
                    .append("/")
                    .append("av_stream")
            ;
            rtspList.add(rtspAddr.toString());
        }

        return rtspList;
    }
    /**
     * 拼接rtsp地址，摄像头品牌：海康威视, 第二种格式
     * @param camera 带通道的设备实体
     * @return 视频流地址
     */
    public static ArrayList<String> concatHikVisionNewAddress(MyCamera camera){
        ArrayList<String> rtspList = new ArrayList<>();
        for(Channel channel: camera.getChannels()){
            StringBuilder rtspAddr = new StringBuilder();
            rtspAddr
                    .append("rtsp://")
                    .append(camera.getLoginName())
                    .append(":")
                    .append(camera.getPassword())
                    .append("@")
                    .append(camera.getIp())
                    .append(":")
                    .append(camera.getPort())
                    .append("/Streaming/Channels/")
                    .append(channel.getNumber())
            ;
            rtspList.add(rtspAddr.toString());
        }
        return rtspList;
    }

    /**
     * 拼接rtsp地址，摄像头品牌：大华
     * @param camera 带通道的设备实体
     * @return 视频流地址
     */
    public static ArrayList<String> concatDaHuaAddress(MyCamera camera){
        ArrayList<String> rtspList = new ArrayList<>();
        for(Channel channel: camera.getChannels()) {
            StringBuilder rtspAddr = new StringBuilder();
            rtspAddr
                    .append("rtsp://")
                    .append(camera.getLoginName())
                    .append(":")
                    .append(camera.getPassword())
                    .append("@")
                    .append(camera.getIp())
                    .append(":")
                    .append(camera.getPort())
                    .append("/cam/realmonitor?channel=ch")
                    .append(channel.getNumber())
                    .append("&subtype=")
                    .append("1")
            ;
            rtspList.add(rtspAddr.toString());
        }
        return rtspList;
    }

    /**
     * 拼接rtsp地址，摄像头品牌：友讯
     * @param camera 带通道的设备实体
     * @return 视频流地址
     */
    public static ArrayList<String> concatDLinkAddress(MyCamera camera){
        ArrayList<String> rtspList = new ArrayList<>();
        for(Channel channel: camera.getChannels()) {
            StringBuilder rtspAddr = new StringBuilder();
            rtspAddr
                    .append("rtsp://")
                    .append(camera.getLoginName())
                    .append(":")
                    .append(camera.getPassword())
                    .append("@")
                    .append(camera.getIp())
                    .append(":")
                    .append(camera.getPort())
                    .append("/live")
                    .append(channel.getNumber())
                    .append(".sdp")
            ;
            rtspList.add(rtspAddr.toString());
        }
        return rtspList;
    }

    /**
     * 拼接rtsp地址，摄像头品牌：安迅士
     * @param camera 带通道的设备实体
     * @return 视频流地址
     */
    public static ArrayList<String> concatAxisAddress(MyCamera camera){
        ArrayList<String> rtspList = new ArrayList<>();
        StringBuilder rtspAddr = new StringBuilder();
        rtspAddr
                .append("rtsp://")
                .append(camera.getLoginName())
                .append(":")
                .append(camera.getPassword())
                .append("@")
                .append(camera.getIp())
                .append("/axis-media/media.amp?videocodec=")
                .append("h264")
                // .append("&resolution=")
                // .append("1920x1080")
        ;
        rtspList.add(rtspAddr.toString());
        return rtspList;
    }

    /**
     * 拼接rtsp地址，根据属性rtspFormat来确定以哪种格式拼接
     * @param camera 摄像头
     * @return 视频流地址数组
     */
    public static ArrayList<String> concatAddress(MyCamera camera){
        String rtspFormat = camera.getRtspFormat();
        if(rtspFormat==null || "".equals(rtspFormat)){
            return new ArrayList<>(Collections.singletonList(""));
        }
        switch (Objects.requireNonNull(rtspFormat)){
            case "HIKVISION":
                return concatHikVisionAddress(camera);
            case "HIKVISION_NEW":
                return concatHikVisionNewAddress(camera);
            case "DAHUA":
                return concatDaHuaAddress(camera);
            case "DLINK":
                return concatDLinkAddress(camera);
            case "AXIS":
                return concatAxisAddress(camera);
            default:
                return concatHikVisionAddress(camera);
        }
    }


    public static HashMap<String, ArrayList<String>> concatAddressForAllFormat(MyCamera camera){
        HashMap<String, ArrayList<String>> res = new HashMap<>();
        res.put("HIKVISION", concatHikVisionAddress(camera));
        res.put("HIKVISION_NEW", concatHikVisionNewAddress(camera));
        res.put("DAHUA", concatDaHuaAddress(camera));
        res.put("DLINK", concatDLinkAddress(camera));
        res.put("AXIS", concatAxisAddress(camera));
        return res;
    }
}
