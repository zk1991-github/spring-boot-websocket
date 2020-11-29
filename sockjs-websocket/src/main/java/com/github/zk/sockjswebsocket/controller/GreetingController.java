package com.github.zk.sockjswebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zk
 * @date 2019/6/17 17:16
 */
@RestController
public class GreetingController {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GreetingController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/greeting")
    // 发送给当前请求的用户
    @SendToUser("/queue/greeting")
    public String handler(String greeting) {
        System.out.println(greeting);
        return greeting;
    }

    /**
     *  从外部发送群消息给主题
     * @param msg
     */
    @RequestMapping("/send")
    public void sendMsg(@RequestParam String msg){
        this.simpMessagingTemplate.convertAndSend("/topic/greeting", msg);
    }

    /**
     * 从外部发送给指定用户
     * @param userName
     * @param msg
     */
    @RequestMapping("/sendToUser")
    public void sendToUser(@RequestParam String userName, String msg) {
        this.simpMessagingTemplate.convertAndSendToUser(userName,"/queue/greeting", msg);
    }

    /**
     * 模拟登陆，发送给指定用户的必要条件
     * 注意localhost和本机IP访问不是同一个Session
     * @param request
     * @param userName
     * @return
     */
    @RequestMapping("/login")
    public boolean login(HttpServletRequest request, @RequestParam String userName) {
        HttpSession session = request.getSession();
        session.setAttribute("login-name", userName);
        return true;
    }
}
