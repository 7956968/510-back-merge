package com.warmnut.log;

import java.util.Date;

import com.warmnut.bean.OperationLog;
import com.warmnut.bean.UserInfo;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.enumerate.LogType;

/**
 * 日志对象创建工厂
 *
 * @author qy
 * @date 2018年9月5日14:02:57
 */
public class LogFactory {

    /**
     * 创建操作日志
     */
    public static OperationLog createOperationLog(LogType logType, Integer userId,String userName, String bussinessName, String clazzName, String methodName, String msg, LogSucceed succeed,String ip) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogType(logType.getMessage());
        operationLog.setLogName(bussinessName);
       // UserInfo user = new UserInfo();
        //user.setId(userId);
        operationLog.setUserId(userId);
        operationLog.setUserName(userName);
//        operationLog.setUser(user);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateTime(new Date());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        operationLog.setIp(ip);
        return operationLog;
    }

   
}
