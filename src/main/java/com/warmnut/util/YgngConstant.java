package com.warmnut.util;

import com.warmnut.enumerate.Protocol;

public class YgngConstant{
	/**日期格式*/
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**日期格式*/
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	/**默认密码*/
	public static final String DEFAULT_PWD = "123456";
	
	public static final String JMS_SUFFIX = ".ygng";
	
	/**开门消息默认的消息存活时间:ms*/
	public static final long JMS_TIME_TO_LIVE = 1000*60;
	
	/**临时配置项,配置生效的协议:true-使用websocket ,false-使用mqtt*/
//	public static final Protocol CONTRACT = Protocol.ASYNC_SOCKET;
	public static final Protocol CONTRACT = Protocol.HTTP;
	
	public static final String separator = "#";
	
	
}