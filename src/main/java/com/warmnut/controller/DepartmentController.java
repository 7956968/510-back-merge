package com.warmnut.controller;

import com.warmnut.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warmnut.bean.Department;
import com.warmnut.service.DepartmentService;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/2 14:53:04
 * 部门控制层
 */
@RestController
@RequestMapping("/department/")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 添加部门
     * @param dept 部门
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(Department dept){
        return departmentService.add(dept);
    }

    /**
     * 通过id删除部门
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return departmentService.deleteById(id);
    }

    /**
     * 批量删除部门
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return departmentService.deleteAll(idList);
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(Department dept){
        return departmentService.updateById(dept);
    }


    /**
     * 查询部门
     * @param params 查询参数
     * @return
     */
    @GetMapping("select")
    public DataResponse<List<Department>> selectAll(PageRequest params){
        return departmentService.selectAll(params);
    }
}
