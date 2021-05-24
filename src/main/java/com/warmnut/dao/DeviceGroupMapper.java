package com.warmnut.dao;

import com.warmnut.bean.DeviceGroup;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/9 18:21
 */
@Mapper
public interface DeviceGroupMapper {
    int insertSelective(DeviceGroup deviceGroup);

    int deleteById(Integer id);

    int deleteAll(@Param(value="idList")ArrayList<Integer> idList);

    /**
     * 删除“设备-分组”关系
     * @param deviceId 设备id
     * @return
     */
    // int deleteRelationByDeviceId(@Param(value = "deviceId")Integer deviceId);

    /**
     * 通过设备id列表，删除“设备-分组”关系
     * @param deviceIdList 设备id列表
     * @return
     */
    // int deleteRelationByDeviceIdList(ArrayList<Integer> deviceIdList);

    int updateByPrimaryKeySelective(DeviceGroup deviceGroup);

    DeviceGroup selectById(Integer id);

    List<DeviceGroup> selectAll(PageRequest params);

    List<Integer> selectByDeviceId(Integer deviceId);

    int removeRelation(@Param(value="deviceIdList")ArrayList<Integer> deviceIdList);

    int insertRelation(@Param(value="deviceIdList")List<Integer> deviceIdList,
                       @Param(value="groupIdList")List<Integer> groupIdList,
                       @Param(value="createUser")Integer createUser);


}
