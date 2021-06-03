package com.warmnut.service.impl;

import com.warmnut.bean.Channel;
import com.warmnut.dao.ChannelMapper;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.enumerate.YgngError;
import com.warmnut.log.LogManager;
import com.warmnut.log.LogTaskFactory;
import com.warmnut.service.ChannelService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.HttpKit;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 17:18
 * 设备通道 服务层实现
 */
@Service("channelService")
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelDao;

    /**
     * 添加通道
     * @param channel 通道实体
     * @return
     */
    public SimpleResponse add(Channel channel) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = channelDao.insertSelective(channel);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        channel.getCreateUser(), null, "添加摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加摄像头通道", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        channel.getCreateUser(), null, "添加摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    channel.getCreateUser(), null, "添加摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "添加摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    /**
     * 通过id删除通道
     * @param id 通道的id
     * @return
     */
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = channelDao.deleteById(id);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "删除摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除摄像头通道", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "删除摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "删除摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "删除摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    /**
     * 批量删除通道
     * @param idList 通道id组成的数据
     * @return
     */
    @Override
    public SimpleResponse deleteAll(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = channelDao.deleteAll(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("批量删除成功");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除摄像头通道", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        null, null, "批量删除摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    null, null, "批量删除摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "批量删除摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    /**
     * 通过id修改通道
     * @param channel 通道实体
     * @return 操作结果
     */
    public SimpleResponse updateById(Channel channel) {
        SimpleResponse res = new SimpleResponse();
        try {
            channel.setUpdateTime(new Date());
            int i = channelDao.updateByPrimaryKeySelective(channel);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        channel.getUpdateUser(), null, "修改摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改摄像头通道", LogSucceed.SUCCESS, HttpKit.getIp())
                );// 保存操作日志
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                        channel.getUpdateUser(), null, "修改摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
                );// 保存操作日志
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(
                    channel.getUpdateUser(), null, "修改摄像头通道", this.getClass().getName(), new Throwable().getStackTrace()[0].getMethodName(), "修改摄像头通道", LogSucceed.FAIL, HttpKit.getIp())
            );// 保存操作日志
        }
        return res;
    }

    /**
     * 通过设备id查找它所有的通道
     * @param deviceId 设备id
     * @return 摄像头通道列表（包含状态信息）
     */
    public DataResponse<List<Channel>> selectByDeviceId(Integer deviceId) {
        List<Channel> channelList = channelDao.selectByDeviceId(deviceId);
        return new DataResponse<List<Channel>>(YgngError.SUCCESS.value(), "查询成功",channelList);
    }
}
