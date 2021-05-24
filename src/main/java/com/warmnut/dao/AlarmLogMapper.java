package com.warmnut.dao;

import com.github.pagehelper.Page;
import com.warmnut.bean.log.AlarmHandleLog;
import com.warmnut.bean.log.AlarmLog;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/18 17:09
 */
@Mapper
public interface AlarmLogMapper {

    /**
     * 插入报警日志
     * @param alarmLog
     * @return
     */
    int insertSelective(AlarmLog alarmLog);

    /**
     * 删除报警日志
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 批量删除报警日志
     * @param idList 报警日志id列表
     * @return
     */
    int deleteAll(ArrayList<Integer> idList);

    // /**
    //  * 修改报警日志
    //  * @param alarmLog
    //  * @return
    //  */
    // int updateByPrimaryKeySelective(AlarmLog alarmLog);

    /**
     * 通过id查找报警日志
     * @param id 报警日志id
     * @return 报警日志
     */
    AlarmLog selectById(int id);

    /**
     * 按条件查询多条报警日志
     * @param params 查询参数
     * @return 报警日志列表
     */
    Page<AlarmLog> selectAll(Map<String, Object> params);


    AlarmHandleLog selectAlarmHandleLogById(@Param("id")Integer id);

    /**
     * 插入报警日志
     * @param alarmHandleLog 报警日志
     * @return 插入结果
     */
    int insertAlarmHandleLog(AlarmHandleLog alarmHandleLog);

    /**
     * 设置报警日志为已处理
     * @param alarmLogIdList 报警日志的id列表
     * @param handleId 处理日志的id
     * @return sql查询记录数
     */
    int setAlarmLogHandled(@Param("alarmLogIdList")ArrayList<Integer> alarmLogIdList,
                           @Param("handleId") Integer handleId
    );
}
