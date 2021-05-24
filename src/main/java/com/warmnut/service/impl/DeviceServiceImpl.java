package com.warmnut.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warmnut.bean.MyCamera;
import com.warmnut.bean.DeviceStream;
import com.warmnut.dao.ChannelMapper;
import com.warmnut.dao.DeviceGroupMapper;
import com.warmnut.util.RtspAddress;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.presets.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warmnut.bean.Device;
import com.warmnut.dao.DeviceMapper;
import com.warmnut.service.DeviceService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.SimpleResponse;

import com.warmnut.enumerate.YgngError;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 11:46
 * 设备 服务层实现
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
    @Autowired
    private DeviceMapper deviceDao;
    @Autowired
    private DeviceGroupMapper deviceGroupDao;

    /**
     * 添加设备
     * @param device
     * @return
     */
    public SimpleResponse add(Device device) {
        SimpleResponse res = new SimpleResponse();
        try {
            // 插入device表，并获取id
            int i = deviceDao.insertSelective(device);
            int j = 0;

            // 分配分组, 分组id存在且不为0时才进行
            if(i>0 && device.getGroupId()!=null && device.getGroupId()!=0){
                HashMap<String,Object> data = new HashMap<String, Object>();
                List<Integer> deviceIdList = new ArrayList<Integer>();
                deviceIdList.add(device.getId());
                List<Integer> groupIdList = new ArrayList<>();
                groupIdList.add(device.getGroupId());
                j = deviceGroupDao.insertRelation(deviceIdList, groupIdList, device.getCreateUser());
                //// 检查异常
            }
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 删除设备
     * @param id 待删除设备的id
     * @return
     */
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceDao.deleteById(id);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 批量删除设备
     * @param idList 设备id组成的列表
     * @return
     */
    @Override
    public SimpleResponse deleteAll(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceDao.deleteAll(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("批量删除成功");
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 修改设备
     * @param device 设备实体
     * @return
     */
    public SimpleResponse updateById(Device device) {
        SimpleResponse res = new SimpleResponse();
        try {
            device.setUpdateTime(new Date());
            int i = deviceDao.updateByPrimaryKeySelective(device);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取设备列表
     * @param params 查询参数：关键字+设备类型
     * @return
     */
    public DataResponse<PageInfo<Device>> selectAll(Map<String,Object> params) {
        String groupId = (String) params.get("groupId");
        int start,length;
        // 设置分页参数
        try{
            start = Integer.parseInt((String)params.get("start"));
            length = Integer.parseInt((String)params.get("length"));
        }catch (NumberFormatException e){
            start = 0;
            length = 10;
        }
        PageHelper.startPage(start, length);
        Page<Device> deviceList = (groupId!=null && groupId.equals("0"))?
                deviceDao.selectNotInGroup(params)
                :deviceDao.selectAll(params);
        return new DataResponse<>(YgngError.SUCCESS.value(), "查询成功", deviceList.toPageInfo());
    }

    /**
     * 获取摄像头列表（通过id列表）
     * @param idList 摄像头id列表
     * @return 摄像头列表
     */
    @Override
    public DataResponse<List<MyCamera>> selectCameras(ArrayList<Integer> idList) {
        List<MyCamera> res = deviceDao.selectCameras(idList);
        return new DataResponse<>(YgngError.SUCCESS.value(), "获取成功", res);
    }

    /**
     * 通过报警设备id获取它联动的摄像头列表
     * @param alarmId 报警设备id
     * @return 摄像头列表
     */
    @Override
    public DataResponse<List<MyCamera>> selectCamerasByAlarmId(Integer alarmId) {
        List<MyCamera> res = deviceDao.selectCamerasByAlarmId(alarmId);
        return new DataResponse<>(YgngError.SUCCESS.value(), "获取成功", res);
    }

    /**
     * 获取有效的视频流rtsp地址列表
     * @return {id,stream}组成的列表
     */
    @Override
    public Map<String, Object> selectStream() {
        Map<String, Object> result=new HashMap<>();
        List<Map<String, Object>> streamList = new ArrayList<>();
        try{
            List<MyCamera> list = deviceDao.selectAllCameras();
            for(MyCamera camera:list){ // 迭代每一个摄像头
                Map<String, Object> map = new HashMap<>();
                ArrayList<String> rtspList = RtspAddress.concatAddress(camera);
                for (String rtspAddr : rtspList) { // 迭代每一个rtsp地址
                    if (isValidStream(rtspAddr)) {
                        map.put("id", camera.getId());
                        map.put("stream", rtspList);
                        streamList.add(map);
                    } else {
                        System.out.println("invalid stream, url:" + rtspList);
                    }
                }

            }
            result.put("errorCode",200);
            result.put("errorMsg","获取成功");
            result.put("device", streamList);
            return result;
        }catch (Exception e){
            result.put("errorCode",500);
            result.put("errorMsg","未知错误");
            result.put("device", streamList);
            return result;
        }
    }

    /**
     * 测试是否是有效的流地址
     * @param rtspStr 流地址
     * @return
     */
    public boolean isValidStream(String rtspStr) {
        boolean isValid = false;
        try {
            System.out.println("创建grabber, url:"+rtspStr);
        	// 创建抓取器，rtsp流需要使用FFmpegFrameGrabber，而不能用FrameGrabber
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspStr);
            // grabber.setOption("rtsp_transport", "tcp");// 以tcp方式连接，而不是udp
            grabber.setOption("stimeout", "2000000");// 设置超时自动断开，避免start方法阻塞，单位微秒，可能会不起作用
            grabber.start();// start方法可能会阻塞
            // 拉取快照
            System.out.println("拉取快照, url:"+rtspStr);
            Frame frame = grabber.grabImage();
            if(null == frame){
                System.out.println("[no image], url: "+rtspStr);
            }else{
                BufferedImage bufferedImage = new Java2DFrameConverter().getBufferedImage(frame);
                byte[] bytes = imageToBytes(bufferedImage, "jpg");
                if(bytes!=null && bytes.length > 0){
                    // try {
                    //     // 保存快照
                    //     File output = new File("C:\\Users\\Administrator\\Desktop\\testjavacv.jpg");
                    //     ImageIO.write(bufferedImage, "jpg", output);
                    // } catch (IOException e) {
                    //     e.printStackTrace();
                    // }
                    isValid = true;
                }
            }
            // 停止并关闭
            grabber.stop();
            grabber.close();
            return isValid;
        }catch(FrameGrabber.Exception e) {
            System.out.println("创建grabber失败, url:"+rtspStr);
            // e.printStackTrace();
            return false;
        }
    }
    /**
     * 图片转字节数组
     * @param bImage 图片数据
     * @param format 格式
     * @return 图片对应的字节数组
     */
    private byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            System.out.println("bufferImage 转 byte 数组异常");
            e.printStackTrace();
            return null;
        }
        return out.toByteArray();
    }

    /**-----------------管理设备联动-----------------------
     * 包括：插入, 批量插入, 删, 查
     */

    /**
     * 添加联动关系
     * @param params
     * @return
     */
    public SimpleResponse insertLinkage(Map<String, Object> params) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceDao.insertLinkage(params);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 批量添加联动关系
     * @param params
     * @return
     */
    public SimpleResponse insertAllLinkage(Map<String, Object> params) {
        SimpleResponse res = new SimpleResponse();
        try {
            params.put("createTime",new Date());
            int i = deviceDao.insertAllLinkage(params);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 删除联动关系
     * @param alarmId 报警器的id
     * @param deviceId 摄像头的id
     * @return
     */
    public SimpleResponse deleteLinkage(Integer alarmId, Integer deviceId) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceDao.deleteLinkage(alarmId,deviceId);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 批量删除联动关系
     * @param idList 待删除的联动的id列表
     * @return
     */
    public SimpleResponse deleteAllLinkageByIdList(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = deviceDao.deleteLinkageByIdList(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取联动关系
     * @param params 暂时无意义
     * @return
     */
    public DataResponse<List<Device>> selectLinkage(Map<String, Object> params) {
        List<Device> deviceList = deviceDao.selectLinkage(params);
        return new DataResponse<List<Device>>(YgngError.SUCCESS.value(), "查询成功",deviceList);
    }
}
