package com.warmnut.dao;


import org.apache.ibatis.annotations.Mapper;
import com.warmnut.bean.User;

@Mapper
public interface UserMapper {
	
	public User selectByJobNumber(String jobNumber);
	
}
