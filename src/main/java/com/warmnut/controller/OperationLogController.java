package com.warmnut.controller;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.OperationLog;
import com.warmnut.service.OperationLogService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2021/5/14 14:17
 */

@RestController
@RequestMapping("/operation_log/")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 通过id删除报警日志
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return operationLogService.deleteById(id);
    }

    /**
     * 批量删除报警日志
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return operationLogService.deleteAll(idList);
    }

    /**
     * 查询报警日志
     * @param params 查询参数
     * @return
     */
    @GetMapping("select")
    public DataResponse<PageInfo<OperationLog>> selectAll(@RequestParam Map<String,Object> params){
        return operationLogService.selectAll(params);
    }
}
