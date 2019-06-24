package com.github.zk.sockjswebsocket.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * @author zk
 * @date 2019/6/21 15:09
 */
public class MyHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        ServletServerHttpRequest httpServletRequest = (ServletServerHttpRequest) request;
        HttpSession session = httpServletRequest.getServletRequest().getSession(false);
        String userName = "";
        if (session == null) {
            System.out.println("未登陆");
        } else {
            userName = (String) session.getAttribute("login-name");
        }

        return new MyPrincipal(userName);
    }
    public class MyPrincipal implements Principal {
        private String userName;
        public MyPrincipal(String userName) {
            this.userName = userName;
        }

        @Override
        public String getName() {
            return userName;
        }
    }
}
