package com.warmnut.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warmnut.dao.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.warmnut.bean.OperationLog;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.OperationLogService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 创建时间：2021/5/14 14:41
 * 操作日志服务层实现
 */
@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogDao;

    /**
     * 删除报警日志
     * @param id 报警日志id
     * @return 操作结果
     */
    @Override
    public SimpleResponse deleteById(Integer id){
        SimpleResponse res = new SimpleResponse();
        try {
            int i = operationLogDao.deleteById(id);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("删除日志成功");
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("不存在对应日志项，无法删除");
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 批量删除操作日志
     * @param idList 操作日志id数组
     * @return 操作结果
     */
    @Override
    public SimpleResponse deleteAll(ArrayList<Integer> idList){
        SimpleResponse res = new SimpleResponse();
        try {
            int i = operationLogDao.deleteAll(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("批量删除成功");
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 关键字查找操作日志
     * @param params 查询参数：关键字，类类型，日期起点，分页起点，每页数量
     * @return 操作日志集合
     */
    @Override
    public DataResponse<PageInfo<OperationLog>> selectAll(Map<String, Object> params){
        int start,length;
        // 设置分页参数
        try{
            start = Integer.parseInt((String)params.get("start"));
            length = Integer.parseInt((String)params.get("length"));
        }catch (NumberFormatException e){
            start = 0;
            length = 10;
        }
        PageHelper.startPage(start, length);
        Page<OperationLog> operationLogList = operationLogDao.selectAll(params);
        return new DataResponse<>(YgngError.SUCCESS.value(), "查询成功",operationLogList.toPageInfo());
    }
}
