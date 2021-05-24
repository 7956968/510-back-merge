package com.warmnut.controller;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.MyCamera;
import com.warmnut.bean.Device;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.DeviceService;
import com.warmnut.util.SimpleResponse;
import com.warmnut.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

/**
 * @author  lupincheng
 * @version  创建时间：2020/12/8 10:46
 * 设备控制层
 */
@RestController
@RequestMapping("/device/")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 添加设备
     * @param device
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(Device device){
        return deviceService.add(device);
    }

    /**
     * 删除设备
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return deviceService.deleteById(id);
    }

    /**
     * 批量删除设备
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return deviceService.deleteAll(idList);
    }

    /**
     * 修改设备
     * @param device
     * @return
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(Device device){
        return deviceService.updateById(device);
    }

    /**
     * 关键字查询设备
     * @param params {keyword:*,type:*,groupId:*}
     * @return
     */
    @GetMapping("select")
    public DataResponse<PageInfo<Device>> selectAll(@RequestParam Map<String,Object> params){
        return deviceService.selectAll(params);
    }

    /**
     * 通过id列表获取多个摄像头设备
     * @param data 查询参数，目前只使用idList
     * @return 摄像头列表
     */
    @PostMapping("select_cameras")
    public DataResponse<List<MyCamera>> selectCameras(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>)(data.get("idList"));
        return deviceService.selectCameras(idList);
    }

    /**
     * 通过报警设备id获取它联动的摄像头列表
     * @param alarmId 报警设备id
     * @return 摄像头列表
     */
    @GetMapping("select_cameras_by_alarm_id")
    public DataResponse<List<MyCamera>> selectCamerasByAlarmId(@RequestParam("alarmId") Integer alarmId){
        if(alarmId==null)
            return new DataResponse<>(YgngError.PARAM_ERROR.value(), "报警设备id不能为空", null);
        return deviceService.selectCamerasByAlarmId(alarmId);
    }

    /**-------------设备联动：将报警设备和摄像头设备联动----------
     * 一共有3个方法：添加, 删除, 查询
     */

    /**
     * 添加单个联动
     * @param params
     * @return
     */
    @PostMapping("addLinkage")
    public SimpleResponse addLinkage(@RequestBody HashMap<String,Object> params){
        return deviceService.insertLinkage(params);
    }

    /**
     * 批量添加联动
     * @param data
     * @return
     */
    @PostMapping("addAllLinkage")
    public SimpleResponse addAllLinkage(@RequestBody HashMap<String,Object> data){
        return deviceService.insertAllLinkage(data);
    }

    /**
     * 删除单个联动
     * @param data
     * @return
     */
    @PostMapping("deleteLinkage")
    public SimpleResponse deleteLinkage(@RequestBody HashMap<String,Object> data){
        Integer alarmId = (Integer)data.get("alarmId");
        Integer deviceId = (Integer)data.get("deviceId");
        return deviceService.deleteLinkage(alarmId,deviceId);
    }

    @PostMapping("deleteAllLinkage")
    public SimpleResponse deleteAllLinkageByIdList(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList =  (ArrayList<Integer>)(data.get("idList"));
        return deviceService.deleteAllLinkageByIdList(idList);
    }

    /**
     * 查询联动
     * @param params 查询参数，alarmId是报警设备id，
     * @return
     */
    @GetMapping("selectLinkage")
    public DataResponse<List<Device>> selectLinkage(@RequestParam Map<String,Object> params){
        return deviceService.selectLinkage(params);
    }
}
