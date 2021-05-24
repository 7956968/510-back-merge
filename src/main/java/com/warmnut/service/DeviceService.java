package com.warmnut.service;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.MyCamera;
import com.warmnut.bean.Device;
import com.warmnut.util.DataResponse;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 10:53
 * 设备 服务层
 */
public interface DeviceService {

    /**
     * 添加设备
     * @param device
     * @return
     */
    SimpleResponse add(Device device);

    /**
     * 删除设备
     * @param id
     * @return
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除设备
     * @param idList 设备id组成的列表
     * @return 简单响应包裹的操作结果
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 修改设备
     * @param device
     * @return
     */
    SimpleResponse updateById(Device device);

    /**
     * 获取设备
     * @param params 查询参数
     * @return
     */
    DataResponse<PageInfo<Device>> selectAll(Map<String,Object> params);

    /**
     * 获取摄像头
     * @param idList id列表
     * @return
     */
    DataResponse<List<MyCamera>> selectCameras(ArrayList<Integer> idList);

    /**
     * 通过报警设备id获取它联动的摄像头列表
     * @param alarmId 报警设备id
     * @return 摄像头列表
     */
    DataResponse<List<MyCamera>> selectCamerasByAlarmId(Integer alarmId);

    /**
     * 获取所有有效的摄像头视频流
     * @return 返回的列表中每一个元素包含两个属性：{id: 摄像头id, stream: 地址}
     */
    Map<String, Object> selectStream();

    /** ------------管理设备联动----------------------------
     * 包括：插入, 批量插入, 删, 查
     */
    SimpleResponse insertLinkage(Map<String,Object> params);

    SimpleResponse insertAllLinkage(Map<String,Object> params);

    SimpleResponse deleteLinkage(Integer alarmId, Integer deviceId);

    SimpleResponse deleteAllLinkageByIdList(ArrayList<Integer> idList);

    DataResponse<List<Device>> selectLinkage(Map<String,Object> params);
}
