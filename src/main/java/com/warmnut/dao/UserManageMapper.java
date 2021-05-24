package com.warmnut.dao;

import com.github.pagehelper.Page;
import com.warmnut.bean.UserManage;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/1 17:54
 */
@Mapper
public interface UserManageMapper {

    int insertSelective(UserManage userManage);

    int deleteById(Integer id);

    int deleteAll(ArrayList<Integer> idList);

    int updateByPrimaryKeySelective(UserManage userManageUpdated);

    UserManage selectById(Integer id);

    /**
     * 查询用户，
     * @param params 查询参数
     * @return 页对象
     */
    Page<UserManage> selectAll(Map<String,Object> params);

    int countByJobNumber(String jobNumber);
}
