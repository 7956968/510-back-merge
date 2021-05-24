package com.warmnut.myWebSocket;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @version 创建时间：2021/3/5 10:54:04
 * websocket，只是一个模板，如果需要具体使用，代码需要修改，然后取消注解的注释
 */
// @ServerEndpoint(value="/websocket/device/{deviceId}")
// @Component("WebSocket")
public class WebSocket {

    private static int onlineCount = 0;// 连接数
    private static final Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;
    private String deviceId;

    @OnOpen
    public void onOpen(@PathParam("deviceId") String deviceId, Session session) throws IOException {
        this.deviceId = deviceId;
        this.session = session;

        addOnlineCount();
        clients.put(deviceId, this);
        System.out.println("[websocket opened]deviceId: " + deviceId);
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(this.deviceId);
        subOnlineCount();
        System.out.println("[websocket closed]deviceId: " + this.deviceId);
    }

    /**
     * 接收监听到的消息
     * @param message json类型的字符串
     * @throws IOException
     * @throws InterruptedException
     */
    @OnMessage
    public void onMessage(String message) throws IOException, InterruptedException {
        if(message==null || message.equals(""))
            return;
        System.out.println("[websocket msg]message:" + message);
        JSONObject req = JSONObject.parseObject(message);
        //		System.out.println("item:" + req.getJSONObject("item"));
        //		System.out.println("item:" + req.getInteger("code"));

        //// 发送数据
        // sendMessageAll(JSON.toJSONString(req));

        JSONObject jsonTo = JSONObject.parseObject(message);
        System.out.println(jsonTo.getString("to") +":"+ jsonTo.getString("msg"));

        if ( ! jsonTo.getString("to").toLowerCase().equals("all")){
            sendMessageTo(jsonTo.getString("msg"), jsonTo.getString("to"));
        }else{
            sendMessageAll(jsonTo.getString("msg"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 单发消息
     * @param message json格式的字符串类型消息
     * @param To 接受者的
     */
    public void sendMessageTo(String message, String To) {
        // session.getBasicRemote().sendText(message); // 阻塞式的（同步）
        // session.getAsyncRemote().sendText(message); // 非阻塞式的（异步）

        WebSocket receiver = clients.get(To);
        if(receiver != null)
            receiver.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发消息
     * @param message json类型的字符串
     */
    public void sendMessageAll(String message){
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 获取已连接的会话数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 连接计数+1
     */
    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    /**
     * 连接计数-1
     */
    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    /**
     * 获取已连接的所有会话组成的map
     * @return
     */
    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }
}
