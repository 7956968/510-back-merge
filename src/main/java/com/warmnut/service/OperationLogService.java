package com.warmnut.service;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.OperationLog;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2021/5/14 14:41
 * 操作日志服务层
 */

public interface OperationLogService {

    /**
     * 删除报警日志
     * @param id 报警日志id
     * @return 操作结果
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除操作日志
     * @param idList 操作日志id数组
     * @return 操作结果
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 关键字查找报警日志
     * @param params 查询参数：关键字，日期起点，分页起点，每页数量
     * @return 操作日志集合
     */
    DataResponse<PageInfo<OperationLog>> selectAll(Map<String, Object> params);
}
