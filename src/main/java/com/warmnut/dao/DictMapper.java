package com.warmnut.dao;

import com.warmnut.bean.Dict;
import com.warmnut.util.PageRequest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface DictMapper {

	/**
	 * 	根据Id删除数据字典
	 * @param id
	 * @return
	 */
    int deleteById(Integer id);

    /**
     * 	筛选插入字典
     * @param record
     * @return
     */
    int insertSelective(Dict record);

    /**
     * 	根据ID查询数据字典
     * @param id
     * @return
     */
    Dict selectById(Integer id);

    /**
     * 	根据编码查询数据字典
     * @param code
     * @return
     */
    Dict selectByCode(String code);
    
    /**
     * 	根据父节点的编码查询所有子节点
     * @param code
     * @return
     */
    List<Dict> selectByParentCode(String code);
    
    /**
     * 	筛选修改数据字典
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Dict record);
    
    /**
     * 	查询所有数据
     * @return
     */
    List<Dict> findAll(PageRequest param);
    
    /**
     * 	分页条件查询查询
     * @return
     */
    List<Dict> pageInfo(Dict dict);

}