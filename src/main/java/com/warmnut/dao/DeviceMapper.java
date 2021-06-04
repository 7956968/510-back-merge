package com.warmnut.dao;

import com.github.pagehelper.Page;
import com.warmnut.bean.MyCamera;
import com.warmnut.bean.Device;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/9 11:37
 */
@Mapper
public interface DeviceMapper {
    /**
     * 插入设备信息，返回设备信息的ID
     * @param device 设备信息
     * @return 插入数据的ID
     */
    int insertSelective(Device device);

    int deleteById(Integer id);

    int deleteAll(ArrayList<Integer> idList);

    int updateByPrimaryKeySelective(Device device);

    /**
     * 设置设备（摄像头）的rtsp格式对应的值
     * @param id 摄像头id
     * @param rtspFormat rtsp格式对应的值
     * @return 受影响的行数
     */
    int setRtspFormat(@Param(value="id")Integer id, @Param(value = "rtspFormat")String rtspFormat);

    Device selectById(Integer id);

    Page<Device> selectAll(Map<String,Object> params);

    Page<Device> selectNotInGroup(Map<String,Object> params);

    /**
     * 获取所有的摄像头
     * 一次性获取所有摄像头太消耗时间，后续需要修改
     * @return 摄像头列表
     */
    ArrayList<MyCamera> selectAllCameras(Map<String,Object> params);

    List<MyCamera> selectCameras(@Param(value="idList")ArrayList<Integer> idList);

    /**
     * 通过报警设备id获取它联动的摄像头列表
     * @param alarmId 报警设备id
     * @return 摄像头列表
     */
    List<MyCamera> selectCamerasByAlarmId(@Param(value="alarmId")Integer alarmId);

    /**-------------管理设备联动--------------
     * 包括：插入, 批量插入, 删, 批量删, 查
     */

    int insertLinkage(Map<String,Object> params);

    int insertAllLinkage(Map<String,Object> params);

    int deleteLinkage(@Param(value="alarmId")Integer alarmId, @Param(value="deviceId")Integer deviceId);

    int deleteLinkageByIdList(ArrayList<Integer> idList);

    List<Device> selectLinkage(Map<String,Object> params);

}
