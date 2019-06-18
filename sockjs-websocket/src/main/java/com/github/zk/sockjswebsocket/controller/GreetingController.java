package com.github.zk.sockjswebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String handler(String greeting) {
        System.out.println(greeting);
        return greeting;
    }

    /**
     *  从外部发送消息给主题
     * @param msg
     */
    @RequestMapping("/send")
    public void sendMsg(String msg){
        this.simpMessagingTemplate.convertAndSend("/topic/greeting", msg);
    }
}
