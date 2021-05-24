package com.warmnut.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import com.warmnut.bean.OperationLog;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface OperationLogMapper{

	/**
	 * 插入操作日志
	 * @param loginLog 操作日志
	 * @return 查询记录数
	 */
	int save(OperationLog loginLog);

	/**
	 * 按条件查询多条操作日志
	 * @param mp 查询参数：关键字，日期起点，（分页起点，每页数量，在服务层通过PageHelper处理
	 * @return
	 */
	Page<OperationLog> selectAll(Map<String,Object> mp);

	/**
	 * 删除操作日志
	 * @param id 待删除的操作日志id
	 * @return 查询记录数
	 */
	int deleteById(Integer id);

	/**
	 * 批量删除操作日志
	 * @param idList 操作日志id列表
	 * @return 查询记录数
	 */
	int deleteAll(ArrayList<Integer> idList);

}
