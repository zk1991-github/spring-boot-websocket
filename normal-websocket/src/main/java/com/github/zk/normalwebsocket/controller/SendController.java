package com.github.zk.normalwebsocket.controller;

import com.github.zk.normalwebsocket.handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @RequestMapping("/sendToUser")
    public String sendMsgToUser(@RequestParam String msg, String userName) {
        for (WebSocketSession webSocketSession : myHandler.webSocketSessions) {
            if (webSocketSession.getAttributes().get("login-name").equals(userName)) {
                try {
                    webSocketSession.sendMessage(new TextMessage(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "已发送";
    }

    /**
     * 模拟登陆，发送至指定用户的必要条件
     * @param request
     * @param name
     * @return
     */
    @RequestMapping("/login")
    public boolean login(HttpServletRequest request, @RequestParam String name) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("login-name",name);
        return true;
    }
}
