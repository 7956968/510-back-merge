package com.warmnut.controller;

import com.warmnut.bean.Channel;
import com.warmnut.service.ChannelService;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 17:14
 * 设备通道控制层
 */
@RestController
@RequestMapping("/channel/")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    /**
     * 添加通道
     * @param channel 通道
     * @return
     */
    @PostMapping("add")
    public SimpleResponse add(Channel channel){
        return channelService.add(channel);
    }

    /**
     * 通过id删除通道
     * @param id
     * @return
     */
    @PostMapping("delete_by_id")
    public SimpleResponse deleteById(Integer id){
        return channelService.deleteById(id);
    }

    /**
     * 批量删除设备通道
     * @param data {idList: id组成的数组 }
     * @return
     */
    @PostMapping("delete_all")
    public SimpleResponse deleteAll(@RequestBody HashMap<String,Object> data){
        ArrayList<Integer> idList = (ArrayList<Integer>) data.get("idList");
        return channelService.deleteAll(idList);
    }

    /**
     * 修改通道
     * @param channel
     * @return
     */
    @PostMapping("update_by_id")
    public SimpleResponse updateById(Channel channel){
        return channelService.updateById(channel);
    }

    // /**
    //  * 查询通道
    //  * @param params 查询参数
    //  * @return
    //  */
    // @GetMapping("select")
    // public DataResponse<List<Channel>> selectAll(PageRequest params){
    //     return channelService.selectAll(params);
    // }

    /**
     * 通过设备id查询通道
     * @param deviceId 设备id
     * @return
     */
    @GetMapping("selectByDeviceId")
    public DataResponse<List<Channel>> selectByDeviceId(Integer deviceId){
        return channelService.selectByDeviceId(deviceId);
    }
}
