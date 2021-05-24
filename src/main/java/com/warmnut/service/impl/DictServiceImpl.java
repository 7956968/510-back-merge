package com.warmnut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warmnut.bean.Dict;
import com.warmnut.dao.DictMapper;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.DictService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

/**
 * @author 作者:zhaoxiaozhou
 * @version 创建时间：2020-10-27 18:24:58
   * 说明
 */
@Service("dictService")
public class DictServiceImpl implements DictService {
	
	@Autowired
	private DictMapper dictDao;
	
	
	@Override
	public DataResponse<List<Dict>> findAll(PageRequest param) {
//		int pageNum = dict.getPageNum();
//        int pageSize = dict.getPageSize();
//        PageHelper.startPage(pageNum, pageSize);
        List<Dict> sysMenus = dictDao.findAll(param);
        return new DataResponse<List<Dict>>(YgngError.SUCCESS.value(), "查询成功",sysMenus);
	}


	@Override
	public SimpleResponse add(Dict dict) {
		SimpleResponse res = new SimpleResponse();
		try {
			Dict d = dictDao.selectByCode(dict.getCode());
			if(d != null) {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg("编码重复");
				return res;
			}
			int i = dictDao.insertSelective(dict);
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}


	@Override
	public SimpleResponse updateById(Dict dict) {
		SimpleResponse res = new SimpleResponse();
		try {
			Dict dict1 = dictDao.selectById(dict.getId());
			dict.setPid(dict1.getPid());
			int i = dictDao.updateByPrimaryKeySelective(dict);
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}


	@Override
	public SimpleResponse deleteById(int id) {
		SimpleResponse res = new SimpleResponse();
		try {
			int i = dictDao.deleteById(id);
			if(i > 0) {
				res.setErrorCode(YgngError.SUCCESS.value());
				res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
			}else {
				res.setErrorCode(YgngError.PARAM_ERROR.value());
				res.setErrorMsg(YgngError.PARAM_ERROR.getReasonPhrase());
			}
		}catch(Exception e) {
			res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
			res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
			e.printStackTrace();
		}
		return res;
	}


	@Override
	public DataResponse<List<Dict>> selectByParentCode(String code) {
		List<Dict> list = dictDao.selectByParentCode(code);
		return new DataResponse<List<Dict>>(YgngError.SUCCESS.value(),"查询成功",list);
	}

}
