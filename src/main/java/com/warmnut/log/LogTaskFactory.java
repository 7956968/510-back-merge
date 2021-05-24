package com.warmnut.log;

import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warmnut.bean.OperationLog;
import com.warmnut.dao.OperationLogMapper;
import com.warmnut.enumerate.LogSucceed;
import com.warmnut.enumerate.LogType;
import com.warmnut.util.SpringContextHolder;

/**
 * 日志操作任务创建工厂
 *
 * @author qy
 * @date 2018年9月5日14:33:15
 */
@Component
public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    
    
   
    
    @Autowired
    private OperationLogMapper operationLogMapper1;

    private static OperationLogMapper operationLogMapper;
    
    @PostConstruct
    public void init() {
      
    	operationLogMapper = operationLogMapper1;

    }
    public static  TimerTask loginLog(final Integer userId, final String userName, final String ip,final LogSucceed succFlag) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                	OperationLog loginLog = LogFactory.createOperationLog(LogType.LOGIN, userId,userName, "登录成功", null, null,  null, succFlag,ip);
                	 operationLogMapper.save(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public  static TimerTask loginFailLog(final Integer userId, final String userName,final String msg, final String ip) {
        return new TimerTask() {
       
            @Override
            public void run() {
            	OperationLog loginLog = LogFactory.createOperationLog(LogType.LOGIN, userId, userName,"登录异常", null, null, "账号:" + userId + "," + msg, LogSucceed.FAIL,ip);
                try {
                	 operationLogMapper.save(loginLog);
                } catch (Exception e) {
                    logger.error("创建日志异常!", e);
                }
            }
        };
    }

    public static  TimerTask exitLog(final Integer userId, final String userName,final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
            	OperationLog loginLog = LogFactory.createOperationLog(LogType.EXIT, userId,userName, "退出系统", null, null, null, LogSucceed.SUCCESS,ip);
                try {
                	 operationLogMapper.save(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Integer userId,final String userName, final String bussinessName, final String clazzName, final String methodName, final String msg,final LogSucceed succFlag,final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId,userName, bussinessName, clazzName, methodName, msg, succFlag,ip);
                try {
                    operationLogMapper.save(operationLog);
                    logger.info("保存日志成功");
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Integer userId,final String userName, final Exception exception,final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = exception.getMessage();
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId,userName, "", null, null, msg, LogSucceed.FAIL,ip);
                try {
                    operationLogMapper.save(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
