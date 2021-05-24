package com.warmnut.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.warmnut.bean.UserManage;
import com.warmnut.service.UserManageService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/02 14:53
 */
@RestController
@RequestMapping("/user_manage/")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    /**
     * 增加用户
     * @param userManage
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(UserManage userManage){
        return userManageService.add(userManage);
    }

    /**
     * 删除用户（不是删除数据，而是将用户的itemStatus属性设为0/false）
     * @param id 用户id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return userManageService.deleteById(id);
    }

    /**
     * 批量删除用户（将用户的itemStatus设为false）
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return userManageService.deleteAll(idList);
    }

    /**
     * 修改用户信息
     * @param userManage
     * @return
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(UserManage userManage){
        return userManageService.updateById(userManage);
    }

    /**
     * 查询用户
     * @param params 查询参数
     * @return
     */
    @GetMapping("select")
    public DataResponse<PageInfo<UserManage>> selectAll(@RequestParam Map<String,Object> params){
        return userManageService.selectAll(params);
    }
}
