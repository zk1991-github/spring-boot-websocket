package com.github.zk.sockjswebsocket.configuration;

import com.github.zk.sockjswebsocket.handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author zk
 * @date 2019/6/17 16:17
 */
@Configuration
public class WebsocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler(),"/websocket")
                .withSockJS();
    }
    @Bean
    public MyHandler myHandler() {
        return new MyHandler();
    }
}
