package com.warmnut.dao;

import com.warmnut.bean.Role;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/2 11:06
 */
@Mapper
public interface RoleMapper {

    int insertSelective(Role role);

    int deleteById(Integer id);

    int deleteAll(ArrayList<Integer> idList);

    int updateByPrimaryKeySelective(Role role);

    Role selectById(Integer id);

    List<Role> selectAll(PageRequest params);

    int countByName(String name);

    Role selectLast();
}
