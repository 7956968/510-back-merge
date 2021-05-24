package com.warmnut.service;
/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-27 17:25:06
   * 说明
 */


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.Dict;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

public interface DictService {
	
	DataResponse<List<Dict>> selectByParentCode(String code);
	
	/**
	 * 	查询全部字典数据
	 * @param dict
	 * @return
	 */
	DataResponse<List<Dict>> findAll(PageRequest param);
	
	/**
	 * 	添加数据字典
	 * @param dict
	 * @return
	 */
	SimpleResponse add(Dict dict);
	
	/**
	 * 	根据ID修改字典
	 * @param dict
	 * @return
	 */
	SimpleResponse updateById(Dict dict);
	
	/**
	 * 	根据ID删除字典
	 * @param dict
	 * @return
	 */
	SimpleResponse deleteById(int id);
}
