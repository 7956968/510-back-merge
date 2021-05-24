package com.warmnut.service;

import com.warmnut.bean.Department;

import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/1 18:15
 */
public interface DepartmentService {

    /**
     * 添加部门
     * @param dept 部门实体
     * @return 响应码
     */
    SimpleResponse add(Department dept);

    /**
     * 删除部门
     * @param id 部门id
     * @return 响应码
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除部门
     * @param idList
     * @return
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 修改部门信息
     * @param dept 部门实体
     * @return
     */
    SimpleResponse updateById(Department dept);

    /**
     * 关键字查找部门
     * @param params
     * @return
     */
    DataResponse<List<Department>> selectAll(PageRequest params);
}
