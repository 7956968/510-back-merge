package com.warmnut.dao;

import com.github.pagehelper.Page;
import com.warmnut.bean.Extinguisher;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;

import com.warmnut.util.PageRequest;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/18 16:10
 */
@Mapper
public interface ExtinguisherMapper {

    int insertSelective(Extinguisher extinguisher);

    int deleteById(Integer id);

    int deleteAll(ArrayList<Integer> idList);

    int updateByPrimaryKeySelective(Extinguisher extinguisherUpdated);

    /**
     * 通过id查找灭火器
     * @param id
     * @return
     */
    Extinguisher selectById(int id);

    /**
     * 查询所有灭火器
     * @param params 查询参数
     * @return
     */
    Page<Extinguisher> selectAll(Map<String, Object> params);
}
