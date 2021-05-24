package com.warmnut.dao;

import com.warmnut.bean.Permission;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper {


	Permission selectByPath(String path);
	
    /**
     * 	根据ID删除菜单
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 	挑选插入数据
     * @param record
     * @return
     */
    int insertSelective(Permission record);

    /**
     * 	根据Id查询菜单
     * @param id
     * @return
     */
    Permission selectById(Integer id);

    /**
     * 	挑选修改数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Permission record);
    
    /**
     * 	查询所有菜单
     * @return
     */
    List<Permission> findAll();
    
    /**
     * 	根据角色Id查询菜单
     * @return
     */
    List<Permission> findByRoleId(int roleId);

    /**
     *  查询最后添加的权限
     * @return
     */
    Permission selectLast();
}