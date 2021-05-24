package com.warmnut.dao;


import com.warmnut.bean.Channel;
import com.warmnut.util.PageRequest;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * @Author lupincheng
 * @Date 创建时间：2020/12/9 17:51
 */
@Mapper
public interface ChannelMapper {
    /**
     * 插入设备通道
     * @param channel 通道
     * @return 查询记录数
     */
    int insertSelective(Channel channel);

    /**
     * 通过id删除通道
     * @param id 通道的id
     * @return 查询记录数
     */
    int deleteById(Integer id);

    /**
     * 批量删除通道
     * @param idList 通道的id列表
     * @return 查询记录数
     */
    int deleteAll(ArrayList<Integer> idList);

    /**
     * 通过设备删除其通道
     * @param deviceId 设备id
     * @return 查询记录数
     */
    // int deleteByDeviceId(Integer deviceId);

    /**
     * 通过设备id列表，批量删除通道
     * @param idList 设备id列表
     * @return
     */
    // int deleteByDeviceIdList(ArrayList<Integer> idList);

    /**
     * 修改通道
     * @param channel 修改后的通道实体
     * @return 查询记录数
     */
    int updateByPrimaryKeySelective(Channel channel);

    /**
     * 查找单个通道（暂时不使用）
     * @param id 通道id
     * @return 查询记录数
     */
    Channel selectById(Integer id);

    // List<Channel> selectAll(PageRequest params);

    /**
     * 通过设备id查找它所有的通道
     * @param deviceId 设备id
     * @return 查询记录数
     */
    List<Channel> selectByDeviceId(Integer deviceId);
}
