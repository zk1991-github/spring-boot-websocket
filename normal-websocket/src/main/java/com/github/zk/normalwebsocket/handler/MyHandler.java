package com.github.zk.normalwebsocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zk
 * @date 2019/6/17 15:52
 */
public class MyHandler extends TextWebSocketHandler {
    public List<WebSocketSession> webSocketSessions = new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("【服务端】已建立连接");
        webSocketSessions.add(session);
        System.out.println("加入用户，当前用户数【" + webSocketSessions.size() +"】");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("【服务端】接收到客户端消息：" + message.getPayload());
        session.sendMessage(new TextMessage("你好"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("【服务端】关闭连接");
        webSocketSessions.remove(session);
        System.out.println("退出用户，当前用户数【" + webSocketSessions.size() + "】");
    }
}
