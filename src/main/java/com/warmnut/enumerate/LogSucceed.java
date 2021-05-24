package com.warmnut.enumerate;

/**
 * 业务是否成功的日志记录
 *
 * @author qy
 * @Date 2018年9月5日14:21:30
 */
public enum LogSucceed {

    SUCCESS("成功"),
    FAIL("失败");

    String message;

    LogSucceed(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
