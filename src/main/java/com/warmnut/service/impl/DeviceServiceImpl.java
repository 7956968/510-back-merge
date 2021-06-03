package com.warmnut.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warmnut.bean.MyCamera;
import com.warmnut.bean.DeviceStream;
import com.warmnut.dao.ChannelMapper;
import com.warmnut.dao.DeviceGroupMapper;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.log.LogManager;
import com.warmnut.log.LogTaskFactory;
import com.warmnut.myWebSocket.StreamWebSocket;
import com.warmnut.util.HttpKit;
import com.warmnut.util.RtspAddress;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.presets.opencv_core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
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

            // 分配分组, 分组id存在且不为0时执行
            if(i>0 && device.getGroupId()!=null && device.getGroupId()!=0){
                HashMap<String,Object> data = new HashMap<String, Object>();
                List<Integer> deviceIdList = new ArrayList<Integer>();
                deviceIdList.add(device.getId());
                List<Integer> groupIdList = new ArrayList<>();
                groupIdList.add(device.getGroupId());
                j = deviceGroupDao.insertRelation(deviceIdList, groupIdList, device.getCreateUser());
            }
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        device.getCreateUser(), null, "添加设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加设备", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        device.getCreateUser(), null, "添加设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加设备", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    device.getCreateUser(), null, "添加设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加设备", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "删除设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除设备", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "删除设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除设备", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "删除设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除设备", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除设备", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除设备", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "批量删除设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除设备", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        device.getUpdateUser(), null, "修改设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改设备", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        device.getUpdateUser(), null, "修改设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改设备", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    device.getUpdateUser(), null, "修改设备", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改设备", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
            // 迭代每一个摄像头
            for(MyCamera camera:list){
                // rtsp地址格式未知，获取所有的拼接形式以尝试进行连接
                if(camera.getRtspFormat()==null || "".equals(camera.getRtspFormat())){
                    HashMap<String, ArrayList<String>> AddrForAllFormat = RtspAddress.concatAddressForAllFormat(camera);
                    // 迭代每一种格式的rtsp地址集，每一个list里面的地址格式相同，仅仅是通道号不同
                    for (Map.Entry<String, ArrayList<String>> entry: AddrForAllFormat.entrySet()) {
                        String rtspAddr = entry.getValue().get(0);// 因为只尝试连接，不必每一个通道试一次，只用第一个通道（下标0）
                        if (isValidStream(rtspAddr)) {// rtsp格式正确
                            for(String str: entry.getValue()){
                                Map<String, Object> tmp = new HashMap<>();// {id:摄像头id, stream:RTSP地址}
                                tmp.put("id", camera.getId());
                                tmp.put("stream", str);
                                streamList.add(tmp);
                            }
                            // rtspFormat存入数据库
                            deviceDao.setRtspFormat(camera.getId(), entry.getKey());
                            break;
                        }
                    }
                }else{// 已确定rtsp地址格式，可以直接拼接
                    List<String> rtspList = RtspAddress.concatAddress(camera);
                    for(String rtspStr: rtspList){
                        Map<String, Object> tmp = new HashMap<>();// {id:摄像头id, stream:RTSP地址}
                        tmp.put("id", camera.getId());
                        tmp.put("stream", rtspStr);
                        streamList.add(tmp);
                    }
                }

            }
            result.put("errorCode",200);
            result.put("errorMsg","获取成功");
            result.put("device", streamList);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("errorCode",500);
            result.put("errorMsg","未知错误");
            result.put("device", streamList);
            return result;
        }
    }

    /**
     * 测试是否是有效的流地址
     * 通过连接摄像头并尝试截取快照，通过此过程成功与否来确定流地址是否有效
     * @param rtspStr 流地址
     * @return 连接摄像头并截取快照成功 ret true；摄像头未启动，或者地址格式错误 ret false
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
        }catch(FrameGrabber.Exception e) {// rtsp地址有误，或者摄像头未启动，...
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        Integer.parseInt((String)params.get("createUser")), null, "添加联动摄像头", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加联动关系", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        Integer.parseInt((String)params.get("createUser")), null, "添加联动摄像头", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加联动关系", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    Integer.parseInt((String)params.get("createUser")), null, "添加联动摄像头", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加联动关系", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        (Integer)params.get("createUser"), null, "批量添加联动摄像头", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量添加联动关系", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        (Integer)params.get("createUser"), null, "批量添加联动摄像头", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量添加联动关系", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    (Integer)params.get("createUser"), null, "批量添加联动摄像头", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量添加联动关系", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "取消联动", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除联动关系", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "取消联动", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除联动关系", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "取消联动", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除联动关系", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
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
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量取消联动", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除联动关系", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量取消联动", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除联动关系", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "批量取消联动", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除联动关系", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    /**
     * 获取联动关系
     * @param params 暂时无意义
     * @return 被联动的摄像头列表（封装成功状态
     */
    public DataResponse<List<Device>> selectLinkage(Map<String, Object> params) {
        List<Device> deviceList = deviceDao.selectLinkage(params);
        return new DataResponse<List<Device>>(YgngError.SUCCESS.value(), "查询成功",deviceList);
    }
}
