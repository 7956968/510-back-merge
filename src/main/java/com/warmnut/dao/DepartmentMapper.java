package com.warmnut.dao;

import com.warmnut.bean.Department;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/2 10:28
 */
@Mapper
public interface DepartmentMapper {

	int insertSelective(Department dept);

    int deleteById(Integer id);

    int deleteAll(ArrayList<Integer> idList);

    int updateByPrimaryKeySelective(Department departmentUpdated);

    /**
     * 通过id查找部门
     * @param id
     * @return
     */
    Department selectById(int id);

    /**
     * 查询所有部门
     * @param params
     * @return
     */
    List<Department> selectAll(PageRequest params);


}
