package com.warmnut.myWebSocket;

import com.alibaba.fastjson.JSONObject;
import com.warmnut.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lupincheng
 * @version 创建时间：2021/4/23 14:32
 * 与前端webSocket长连接，用于发送报警信息
 */
@ServerEndpoint(value="/websocket/browser/{sessionId}")
@Component("BrowserWebSocket")
@EnableScheduling  // 启动定时任务
public class BrowserWebSocket {
    // 日志处理
    private static final Logger logger = LoggerFactory.getLogger(BrowserWebSocket.class);

    private static int onlineCount = 0;// 连接数
    private static final ConcurrentHashMap<String, BrowserWebSocket> clients = new ConcurrentHashMap<String, BrowserWebSocket>();// 存储会话
    private Session session;// 本实例中的会话
    private String sessionId;// 会话唯一标识/索引
    private static final String heartBeatStr="{\"method\":\"HeartBeat\"}";// 心跳检测的json字符串

    private static DeviceService deviceService;
    @Autowired
    public void setDeviceService(DeviceService deviceService){
        BrowserWebSocket.deviceService = deviceService;
    }

    /**
     * 会话连接成功时触发
     * @param sessionId 会话唯一标识符
     * @param session 会话
     */
    @OnOpen
    public void onOpen(@PathParam("sessionId") String sessionId, Session session) {
        this.sessionId = sessionId;
        this.session = session;
        addOnlineCount();
        clients.put(sessionId, this);
        logger.info("websocket opened, sessionId[" + sessionId+"]");
        // this.sendHeartBeat(); // 发送心跳包
    }

    /**
     * 会话关闭时触发
     * @param session 被关闭的会话
     * @param closeReason 关闭原因
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        clients.remove(sessionId);
        subOnlineCount();
        logger.info("websocket closed, sessionId[" + sessionId+ "], close reason {"+closeReason+"}");
    }

    /**
     * 接收监听到的消息
     * @param message json对象转化成的字符串
     *                如果message为空或者由一长串空白符组成，则报错java.lang.NullPointerException
     */
    @OnMessage
    public void onMessage(String message) {
        if(message==null || message.equals(""))
            return;
        logger.info("websocket message received: "+message);
        JSONObject jsonObj;
        try{
            jsonObj = JSONObject.parseObject(message);
        }catch (Exception e){ // 不是json格式
            return;
        }

        String method = jsonObj.getString("method");
        //// dosth()
        // if("getStream".equals(method)){
        //     Map<String, Object> resultObj = deviceService.selectStream();
        //     // obj -> jsonString
        //     String res = JSONObject.toJSONString(resultObj);
        //     this.session.getAsyncRemote().sendText(res);
        // }
    }

    /**
     * 会话出错时触发
     * @param session 出错的会话
     * @param error 错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("websocket error, sessionId["+sessionId+"]");
        error.printStackTrace();
    }

    // /**
    //  * 发送心跳包，避免会话等待超时而关闭
    //  * 当前设置为5秒一次
    //  * initialDelay, fixedRate的单位是毫秒
    //  */
    // @Scheduled(fixedRate=5*1000)
    // private void sendHeartBeat() {
    //     sendMessageAll(heartBeatStr);
    // }

    /**
     * 发送消息
     * @param message json对象转化成的字符串
     * @param To 接收者的sessionId
     */
    public void sendMessageTo(String message, String To) {
        // session.getBasicRemote().sendText(message); // 阻塞式的（同步）
        // session.getAsyncRemote().sendText(message); // 非阻塞式的（异步）

        BrowserWebSocket receiver = clients.get(To);
        if(receiver != null)
            receiver.session.getAsyncRemote().sendText(message);
    }

    /**
     * 多发消息
     * @param message json对象转化成的字符串
     */
    public static void sendMessageAll(String message) {
        for (BrowserWebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 获取已连接的会话数
     * @return 已连接的会话数
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 连接计数+1
     */
    public static synchronized void addOnlineCount() {
        BrowserWebSocket.onlineCount++;
    }

    /**
     * 连接计数-1
     */
    public static synchronized void subOnlineCount() {
        BrowserWebSocket.onlineCount--;
    }

    /**
     * 获取已连接的所有会话组成的map
     * @return 已连接的所有会话组成的map
     */
    public static synchronized Map<String, BrowserWebSocket> getClients() {
        return clients;
    }
}
