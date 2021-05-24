package com.warmnut.service;

import com.warmnut.bean.Channel;
import com.warmnut.util.DataResponse;
import com.warmnut.util.PageRequest;
import com.warmnut.util.SimpleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lupincheng
 * @version 创建时间：2020/12/9 17:16
 * 设备通道 服务层
 */
public interface ChannelService {

    /**
     * 添加通道
     * @param channel 通道实体
     * @return
     */
    SimpleResponse add(Channel channel);

    /**
     * 通过id删除通道
     * @param id 通道的id
     * @return
     */
    SimpleResponse deleteById(Integer id);

    /**
     * 批量删除通道
     * @param idList 通道id组成的数据
     * @return
     */
    SimpleResponse deleteAll(ArrayList<Integer> idList);

    /**
     * 通过id修改通道
     * @param channel 通道实体
     * @return
     */
    SimpleResponse updateById(Channel channel);

    // DataResponse<List<Channel>> selectAll(PageRequest params);

    /**
     * 通过设备id查找它所有的通道
     * @param deviceId 设备id
     * @return
     */
    DataResponse<List<Channel>> selectByDeviceId(Integer deviceId);
}
