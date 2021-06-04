package com.warmnut.controller;

import com.warmnut.bean.DeviceGroup;
import com.warmnut.service.DeviceGroupService;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;
import com.warmnut.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  lupincheng
 * @version  创建时间：2020/12/9 18:29
 * 设备分组控制层
 */
@RestController
@RequestMapping("/group/")
public class DeviceGroupController {
    @Autowired
    private DeviceGroupService deviceGroupService;

    /**
     * 添加分组
     * @param deviceGroup
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(DeviceGroup deviceGroup){
        return deviceGroupService.add(deviceGroup);
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return deviceGroupService.deleteById(id);
    }

    /**
     * 删除所有id在data的idList中的分组
     * @param data
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList =  ((ArrayList<Integer>)(data.get("idList")));
        return deviceGroupService.deleteAll(idList);
    }

    /**
     * 修改分组（分组名|父分组）
     * @param deviceGroup 分组
     * @return 操作结果
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(DeviceGroup deviceGroup){
        return deviceGroupService.updateById(deviceGroup);
    }

    /**
     * 关键字查询分组
     * @param params （暂时无意义，以后可能通过关键词查询）
     * @return 分组列表
     */
    @GetMapping("select")
    public DataResponse<List<DeviceGroup>> selectAll(PageRequest params){
        return deviceGroupService.selectAll(params);
    }

    /**
     * 通过设备id获取设备所在的所有分组
     * @param params {"deviceId":xxx}
     * @return
     */
    @GetMapping("select_by_device_id")
    public DataResponse<List<Integer>> selectByDeviceId(@RequestParam Map<String,Object> params){
        Integer deviceId = Integer.valueOf((String)params.get("deviceId"));
        return deviceGroupService.selectByDeviceId(deviceId);
    }

    /**
     * 关键字查询分组，包括分组所有的设备
     * 不包括默认分组（id=0）以及不在分组中的设备
     * @param params 暂时无意义，后续考虑添加筛选参数
     * @return 带摄像头的分组列表
     */
    @GetMapping("select_with_devices")
    public DataResponse<List<DeviceGroup>> selectWithDevices(PageRequest params){
        return deviceGroupService.selectWithDevices(params);
    }

    // /**
    //  * 移动设备到另外的分组
    //  * @param data
    //  *      deviceIdList: [xxx,xxx,xxx,...],
    //  *      groupId: *, 如果为null, 则移至未分组设备
    //  * @return
    //  */
    // @PostMapping("move_devices")
    // public SimpleResponse moveRelation(@RequestBody HashMap<String,Object> data){
    //     return deviceGroupService.moveRelation(data);
    // }

    /**
     * 为多个设备分配多个分组
     * @param data
     *      deviceIdList: [xxx,xxx,xxx,...],
     *      groupIdList: [xxx,xxx,xxx,...], 如果为空，则设为未分组设备
     * @return
     */
    @PostMapping("distribute_devices_to groups")
    public SimpleResponse distributeDevicesToGroups(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> deviceIdList = (ArrayList<Integer>) data.get("deviceIdList");
        ArrayList<Integer> groupIdList = (ArrayList<Integer>) data.get("groupIdList");
        Integer createUser = (Integer) data.get("createUser");
        return deviceGroupService.distributeDevicesToGroups(deviceIdList, groupIdList, createUser);
    }
}
