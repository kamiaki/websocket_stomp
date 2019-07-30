package com.cnblogs.yjmyzz.websocket.demo.config;

import com.cnblogs.yjmyzz.websocket.demo.consts.GlobalConsts;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * @author junmingyang
 * 这个配置的主要作用是对外暴露访问的端点，以及定义客户端访问时，收发消息的方法url前缀。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(GlobalConsts.main_receive_url);
        config.setApplicationDestinationPrefixes(GlobalConsts.main_send_url);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(GlobalConsts.connect_sw_url).withSockJS();
    }

}