package com.warmnut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warmnut.bean.Department;
import com.warmnut.dao.DepartmentMapper;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.DepartmentService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/2 10:26
 * 部门 服务层实现
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentDao;

    @Override
    public SimpleResponse add(Department dept) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = departmentDao.insertSelective(dept);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = departmentDao.deleteById(id);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("删除部门成功");
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("不存在对应部门，无法删除");
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public SimpleResponse deleteAll(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = departmentDao.deleteAll(idList);
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

    @Override
    public SimpleResponse updateById(Department dept) {
        SimpleResponse res = new SimpleResponse();
        try {
            dept.setUpdateTime(new Date());
            int i = departmentDao.updateByPrimaryKeySelective(dept);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg(YgngError.NO_DATA.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public DataResponse<List<Department>> selectAll(PageRequest params) {
        List<Department> deptList = departmentDao.selectAll(params);
        return new DataResponse<List<Department>>(YgngError.SUCCESS.value(), "查询成功",deptList);
    }
}
