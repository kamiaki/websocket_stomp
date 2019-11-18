package com.cnblogs.yjmyzz.websocket.demo.javaClient;

import com.cnblogs.yjmyzz.websocket.demo.consts.GlobalConsts;
import com.cnblogs.yjmyzz.websocket.demo.model.ClientMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @author junmingyang
 */
@Slf4j
public class DemoWebSocketClient {
    //连接地址url
    public static final String CONNECT_URL = "ws://localhost:8080" + GlobalConsts.connect_sw_url;
    //发送url
    public static final String SEND_URL = GlobalConsts.main_send_url + GlobalConsts.send_url1;
    //接收订阅url
    public static final String RECEIVE_URL = GlobalConsts.main_receive_url + GlobalConsts.receive_url1;

    public static void main(String[] args) throws Exception {
        //固定内容
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String name = "王小秋" + System.currentTimeMillis();//用户名
        //初始化连接
        StompSessionHandler sessionHandler = new MyStompSessionHandler(name, SEND_URL, RECEIVE_URL);
        //进行连接
        StompSession session = stompClient.connect(CONNECT_URL, sessionHandler).get();

        //发送消息
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for (; ; ) {
            String line = in.readLine();
            if (line == null) break;
            if (line.length() == 0) continue;
            ClientMessage msg = new ClientMessage("发送的消息是:" + line);
            session.send(SEND_URL, msg);
        }
    }
}