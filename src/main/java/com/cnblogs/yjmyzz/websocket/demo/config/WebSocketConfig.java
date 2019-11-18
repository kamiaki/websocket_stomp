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
        //在这里设置订阅接收的主地址，和用户订阅的主地址
        config.enableSimpleBroker(GlobalConsts.userTestUrl, GlobalConsts.main_receive_url);
        //用convertAndSendToUser来发送消息，用户订阅前缀
        config.setUserDestinationPrefix(GlobalConsts.userTestUrl);
        //在这里设置发送的主地址
        config.setApplicationDestinationPrefixes(GlobalConsts.main_send_url);
    }

    //设置websocket，connect地址
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //设置websocket，connect地址
        registry.addEndpoint(GlobalConsts.connect_sw_url).withSockJS();
    }

}