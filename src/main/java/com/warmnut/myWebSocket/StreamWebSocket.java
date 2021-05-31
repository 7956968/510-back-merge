package com.warmnut.myWebSocket;

import com.alibaba.fastjson.JSONObject;
import com.warmnut.bean.log.AlarmLog;
import com.warmnut.dao.AlarmLogMapper;
import com.warmnut.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lupincheng
 * @version 创建时间：2021/3/5 10:54:04
 * websocket长连接
 * 为会话对方提供rtsp格式的视频流地址
 * 并接收对方发送的报警信息{"id":"xxx","desc":"xxx"}, id表示摄像头id，desc是报警具体描述
 */
@ServerEndpoint(value="/websocket/stream/{sessionId}")
@Component("StreamWebSocket")
@EnableScheduling // 启动定时任务
public class StreamWebSocket {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(StreamWebSocket.class);

    private static int onlineCount = 0;// 连接数
    private static final ConcurrentHashMap<String, StreamWebSocket> clients = new ConcurrentHashMap<String, StreamWebSocket>();// 存储会话
    private Session session;// 会话
    private String sessionId;// 会话唯一标识/索引
    private static final String heartBeatStr="{\"method\":\"HeartBeat\"}";// 心跳检测的json字符串

    private static BrowserWebSocket browserWebSocket;
    private static DeviceService deviceService;
    private static AlarmLogMapper alarmLogMapper;

    @Autowired
    public void setBrowserWebSocket(BrowserWebSocket browserWebSocket){
        StreamWebSocket.browserWebSocket = browserWebSocket;
    }
    @Autowired
    public void setDeviceService(DeviceService deviceService){
        StreamWebSocket.deviceService = deviceService;
    }
    @Autowired
    public void setAlarmLogMapper(AlarmLogMapper alarmLogMapper){
        StreamWebSocket.alarmLogMapper = alarmLogMapper;
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
        this.sendHeartBeat(); // 发送心跳包
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
        // 接收到的是获取视频流
        if("getStream".equals(method)){
            Map<String, Object> resultObj = deviceService.selectStream();
            // obj -> jsonString
            String res = JSONObject.toJSONString(resultObj);
            this.session.getAsyncRemote().sendText(res);
        }else if("alarmMessage".equals(method)){ // 接收到的是发送报警信息
            String cameraId = jsonObj.getString("cameraId");  // 摄像头id
            String alarmId = jsonObj.getString("alarmId");  // 报警设备id
            String name = jsonObj.getString("name");  // 名称
            String description = jsonObj.getString("description");  // 描述
            String location = jsonObj.getString("location");  // 位置
            String alarmTime = jsonObj.getString("alarmTime");  // 报警时间
            // String recordTime = jsonObj.getString("recordTime");  // 记录时间

            //// 存入报警日志
            AlarmLog alarmLog = new AlarmLog();
            alarmLog.setName(name);
            alarmLog.setDescription(description);
            alarmLog.setLocation(location);
            try{ // 存日期，格式转换
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(alarmTime);
                alarmLog.setAlarmTime(date);
            }catch (ParseException e1){
                logger.error("报警日志设置报警时间异常，报警时间字符串：["+alarmTime+"]");
            }
            alarmLogMapper.insertSelective(alarmLog);
            //// 发送至浏览器
            BrowserWebSocket.sendMessageAll(JSONObject.toJSONString(jsonObj));
        }
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

    /**
     * 发送心跳包，避免会话等待超时而关闭
     * 当前设置为5秒一次
     * initialDelay, fixedRate的单位是毫秒
     */
    @Scheduled(fixedRate=5*1000)
    private void sendHeartBeat() {
        sendMessageAll(heartBeatStr);
    }

    /**
     * 发送消息
     * @param message json对象转化成的字符串
     * @param To 接收者的sessionId
     */
    public void sendMessageTo(String message, String To) {
        // session.getBasicRemote().sendText(message); // 阻塞式的（同步）
        // session.getAsyncRemote().sendText(message); // 非阻塞式的（异步）

        StreamWebSocket receiver = clients.get(To);
        if(receiver != null)
            receiver.session.getAsyncRemote().sendText(message);
    }

    /**
     * 多发消息
     * @param message json对象转化成的字符串
     */
    public static void sendMessageAll(String message) {
        for (StreamWebSocket item : clients.values()) {
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
        StreamWebSocket.onlineCount++;
    }

    /**
     * 连接计数-1
     */
    public static synchronized void subOnlineCount() {
        StreamWebSocket.onlineCount--;
    }

    /**
     * 获取已连接的所有会话组成的map
     * @return 已连接的所有会话组成的map
     */
    public static synchronized Map<String, StreamWebSocket> getClients() {
        return clients;
    }
}
