package com.warmnut.controller;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.Extinguisher;
import com.warmnut.service.ExtinguisherService;
import com.warmnut.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/18 15:54
 * 灭火器信息控制层
 */
@RestController
@RequestMapping("/extinguisher/")
public class ExtinguisherController {

    @Autowired
    private ExtinguisherService extinguisherService;

    /**
     * 添加灭火器信息
     * @param extinguisher 灭火器
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(Extinguisher extinguisher){
        return extinguisherService.add(extinguisher);
    }

    /**
     * 删除灭火器信息
     * @param id 灭火器id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return extinguisherService.deleteById(id);
    }

    /**
     * 批量删除灭火器信息
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return extinguisherService.deleteAll(idList);
    }

    /**
     * 修改灭火器信息
     * @param extinguisher 灭火器
     * @return
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(Extinguisher extinguisher){
        return extinguisherService.updateById(extinguisher);
    }

    /**
     * 查询灭火器信息
     * @param params 查询参数
     * @return
     */
    @GetMapping("select")
    public DataResponse<PageInfo<Extinguisher>> selectAll(@RequestParam Map<String, Object> params){
        return extinguisherService.selectAll(params);
    }
}
