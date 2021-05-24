package com.warmnut.service;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.Extinguisher;

import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/18 16:15
 * 灭火器信息 服务层
 */
public interface ExtinguisherService {
    /**
     * 添加灭火器
     * @param extinguisher 灭火器
     * @return 响应码
     */
    SimpleResponse add(Extinguisher extinguisher);

    /**
     * 删除灭火器
     * @param id 灭火器id
     * @return 响应码
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除灭火器信息
     * @param idList
     * @return
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 修改灭火器信息
     * @param extinguisher 灭火器实体
     * @return
     */
    SimpleResponse updateById(Extinguisher extinguisher);

    /**
     * 关键字查找灭火器
     * @param params
     * @return
     */
    DataResponse<PageInfo<Extinguisher>> selectAll(Map<String, Object> params);
}
