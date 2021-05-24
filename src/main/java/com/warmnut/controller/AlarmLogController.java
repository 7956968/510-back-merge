package com.warmnut.controller;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.log.AlarmHandleLog;
import com.warmnut.enumerate.YgngError;
import com.warmnut.util.DataResponse;
import com.warmnut.util.MyTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.warmnut.bean.log.AlarmLog;
import com.warmnut.service.AlarmLogService;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/18 17:21
 * 报警日志控制层
 */

@RestController
@RequestMapping("/alarm_log/")
public class AlarmLogController {
    @Autowired
    private AlarmLogService alarmLogService;

    /**
     * 通过id删除报警日志
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return alarmLogService.deleteById(id);
    }

    /**
     * 批量删除报警日志
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return alarmLogService.deleteAll(idList);
    }

    /**
     * 查询报警日志
     * @param params 查询参数
     * @return
     */
    @GetMapping("select")
    public DataResponse<PageInfo<AlarmLog>> selectAll(@RequestParam Map<String, Object> params){
        return alarmLogService.selectAll(params);
    }

    /**
     * 获取报警处理日志
     * @param id 报警处理日志id
     * @return 报警处理日志 or 错误信息
     */
    @GetMapping("select_alarm_handle_log")
    public DataResponse<AlarmHandleLog> selectAlarmHandleLogById(@RequestParam("id")Integer id){
        return alarmLogService.selectAlarmHandleLogById(id);
    }

    /**
     * 添加报警处理日志
     * @param data 【alarmHandleLog: 报警处理日志, alarmLogIdList:报警日志id列表】
     * @return 报警处理日志 or 错误信息
     */
    @PostMapping("add_alarm_handle_log")
    public SimpleResponse addAlarmHandleLog(@RequestBody HashMap<String, Object> data){
        try{
            // 取出报警日志id列表
            ArrayList<Integer> alarmLogIdList = (ArrayList<Integer>) data.get("alarmLogIdList");
            // 取出处理日志对象
            Map<String, Object> mp = (Map<String, Object>) data.get("alarmHandleLog");
            AlarmHandleLog alarmHandleLog = new AlarmHandleLog();
            alarmHandleLog.setDescription((String)mp.get("description"));
            alarmHandleLog.setType((String)mp.get("type"));
            alarmHandleLog.setUserId((Integer)mp.get("userId"));
            return alarmLogService.addAlarmHandleLog(alarmLogIdList, alarmHandleLog);
        }catch (ClassCastException e){
            return new SimpleResponse(YgngError.PARAM_ERROR.value(), "添加处理信息失败(类型异常)");
        }

    }

}
