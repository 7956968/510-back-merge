package com.warmnut.service.impl;

import com.github.pagehelper.PageInfo;
import com.warmnut.bean.UserManage;
import com.warmnut.dao.UserManageMapper;
import com.warmnut.enumerate.YgngError;
import com.warmnut.service.UserManageService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/2 16:28
 * 用户 服务层实现
 */
@Service("userService")
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    private UserManageMapper userDao;

    /**
     * 添加用户
     * 先进行工号判重，密码加密，最终再添加
     * @param userManage
     * @return
     */
    public SimpleResponse add(UserManage userManage) {
        SimpleResponse res = new SimpleResponse();
        try {
            // 工号判重
            int userFound = userDao.countByJobNumber(userManage.getJobNumber());
            if(userFound > 0){
                res.setErrorCode(YgngError.PARAM_ERROR.value());
                res.setErrorMsg("工号重复");
                return res;
            }
            // 密码加密
            String encoder = new BCryptPasswordEncoder().encode(userManage.getPassword());
            userManage.setPassword(encoder);
            // 插入
            int i = userDao.insertSelective(userManage);
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

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    public SimpleResponse deleteById(Integer id) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = userDao.deleteById(id);
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
    public SimpleResponse deleteAll(ArrayList<Integer> idList) {
        SimpleResponse res = new SimpleResponse();
        try {
            int i = userDao.deleteAll(idList);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg("批量删除成功");
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg("删除失败，未查找到对应的数据");
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 修改用户信息
     * 1. 若修改工号，需要查重
     * 2. 若修改密码，需要加密
     *
     * @param user 用户实体
     * @return
     */
    public SimpleResponse updateById(UserManage user) {
        SimpleResponse res = new SimpleResponse();
        try {
            // 若修改工号，需要查重
            UserManage userFound = userDao.selectById(user.getId());
            if( ! userFound.getJobNumber().equals(user.getJobNumber()) ){
                int jobNumberFound = userDao.countByJobNumber(user.getJobNumber());
                if(jobNumberFound > 0){
                    res.setErrorCode(YgngError.PARAM_ERROR.value());
                    res.setErrorMsg("工号重复");
                    return res;
                }
            }
            // 密码加密
            if(user.getPassword() != null){
                String encoder = new BCryptPasswordEncoder().encode(user.getPassword());
                user.setPassword(encoder);
            }
            user.setModifyTime(new Date());
            int i = userDao.updateByPrimaryKeySelective(user);
            if(i > 0) {
                res.setErrorCode(YgngError.SUCCESS.value());
                res.setErrorMsg(YgngError.SUCCESS.getReasonPhrase());
            }else {
                res.setErrorCode(YgngError.NO_DATA.value());
                res.setErrorMsg(YgngError.NO_DATA.getReasonPhrase());
            }
        }catch(Exception e) {
            res.setErrorCode(YgngError.UNKNOWN_ERROR.value());
            res.setErrorMsg(YgngError.UNKNOWN_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
        return res;
    }

    public DataResponse<PageInfo<UserManage>> selectAll(Map<String,Object> params) {
        Object start = params.get("start");
        Object length = params.get("length");
        if(start!=null && length!=null)
            PageHelper.startPage(Integer.parseInt((String)start) , Integer.parseInt((String)length));
        else
            PageHelper.startPage(0 , 10);
        Page<UserManage> res = userDao.selectAll(params);
        return new DataResponse<>(YgngError.SUCCESS.value(), "查询成功", res.toPageInfo());
    }

}
