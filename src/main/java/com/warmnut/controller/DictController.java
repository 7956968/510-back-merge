package com.warmnut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.Dict;
import com.warmnut.service.DictService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-27 19:05:16
   * 说明
 */
@RestController
@RequestMapping("/dict/")
public class DictController {

	
	@Autowired
	private DictService dictService;
	
	@GetMapping("select_info")
	public DataResponse<List<Dict>> selectPageInfo(PageRequest param){
		return dictService.findAll(param);
	}
	
	@GetMapping("selectByParentCode")
	public DataResponse<List<Dict>> selectByParentCode(String code){
		return dictService.selectByParentCode(code);
	}
	
	@PostMapping("add")
	public SimpleResponse add(Dict dict){
		return dictService.add(dict);
	}
	
	@PostMapping("update_by_id")
	public SimpleResponse updateById(Dict dict){
		return dictService.updateById(dict);
	}
	
	@PostMapping("delete_by_id")
	public SimpleResponse deleteById(Integer id){
		return dictService.deleteById(id);
	}
}
