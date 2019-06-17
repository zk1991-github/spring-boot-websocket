package com.github.zk.normalwebsocket.controller;

import com.github.zk.normalwebsocket.handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ListIterator;

/**
 * @author zk
 * @date 2019/6/17 16:09
 */
@RestController
public class SendController {
    @Autowired
    private MyHandler myHandler;
    @RequestMapping("/send")
    public String sendMsg(@RequestParam String msg) {
        ListIterator<WebSocketSession> listIterator = myHandler.webSocketSessions.listIterator();
        while (listIterator.hasNext()) {
            WebSocketSession session = listIterator.next();
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "已发送";
    }
}
