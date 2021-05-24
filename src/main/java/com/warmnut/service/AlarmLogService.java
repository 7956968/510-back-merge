package com.warmnut.service;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.log.AlarmHandleLog;
import com.warmnut.bean.log.AlarmLog;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  lupincheng
 * @version  创建时间：2020/12/18 17:13
 * 报警日志 服务层
 */
public interface AlarmLogService {

    /**
     * 添加报警日志
     * @param alarmLog 报警日志
     * @return 响应码
     */
    SimpleResponse add(AlarmLog alarmLog);

    /**
     * 删除报警日志
     * @param id 报警日志id
     * @return 响应码
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除报警日志
     * @param idList 报警日志id列表
     * @return 操作结果
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 关键字查找报警日志
     * @param params 查询参数
     * @return 报警日志列表 or 错误信息
     */
    DataResponse<PageInfo<AlarmLog>> selectAll(Map<String, Object> params);

    /**
     * 获取报警处理日志
     * @param id 报警处理日志id
     * @return 报警处理日志 or 错误信息
     */
    DataResponse<AlarmHandleLog> selectAlarmHandleLogById(Integer id);

    /**
     * 添加报警处理日志
     * @param alarmLogIdList 被处理的报警日志的id列表
     * @param alarmHandleLog 报警处理日志
     * @return 报警处理日志 or 错误信息
     */
    SimpleResponse addAlarmHandleLog(ArrayList<Integer> alarmLogIdList, AlarmHandleLog alarmHandleLog);
}
