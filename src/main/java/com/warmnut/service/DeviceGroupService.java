package com.warmnut.service;

import com.warmnut.bean.DeviceGroup;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 18:23
 * 设备分组 服务层
 */
public interface DeviceGroupService {
    /**
     * 添加分组
     * @param deviceGroup 分组
     * @return
     */
    SimpleResponse add(DeviceGroup deviceGroup);

    /**
     * 删除分组
     * @param id 分组id
     * @return
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除分组
     * @param idList 分组id组成的数组
     * @return
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 修改分组
     * @param deviceGroup 分组实体
     * @return
     */
    SimpleResponse updateById(DeviceGroup deviceGroup);

    /**
     * 获取所有分组
     * @param params 查询参数（暂时无意义）
     * @return
     */
    DataResponse<List<DeviceGroup>> selectAll(PageRequest params);

    /**
     * 通过设备id获取设备所在的所有分组
     * @param deviceId 设备id
     * @return
     */
    DataResponse<List<Integer>> selectByDeviceId(Integer deviceId);

    /**
     * 获取带设备的分组（测试用方法，待调整）
     * @param params
     * @return
     */
    DataResponse<List<DeviceGroup>> selectWithDevices(PageRequest params);

    // 移动 device-group 关系
    // SimpleResponse moveRelation(Map<String,Object> params);

    /**
     * 为多个设备分配多个分组。先删除设备原有的分组，再添加数据。
     * @param deviceIdList 设备id数组
     * @param groupIdList 分组id数组
     * @param createUser 创建者id
     * @return 响应结果
     */
    SimpleResponse distributeDevicesToGroups(ArrayList<Integer> deviceIdList,
                                             ArrayList<Integer> groupIdList,
                                             Integer createUser);
}
