package com.warmnut.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.warmnut.bean.UserManage;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/1 18:14
 * 用户 服务层
 */
public interface UserManageService {

    /**
     * 添加用户
     * @param userManage 用户实体
     * @return
     */
    SimpleResponse add(UserManage userManage);

    /**
     * 删除用户
     * @param id 用户id
     * @return
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除用户
     * @param idList
     * @return
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 修改用户信息
     * @param userManage 用户实体
     * @return
     */
    SimpleResponse updateById(UserManage userManage);

    /**
     * 查询用户
     * @param params 查询参数
     * @return
     */
    DataResponse<PageInfo<UserManage>> selectAll(Map<String,Object> params);
}
