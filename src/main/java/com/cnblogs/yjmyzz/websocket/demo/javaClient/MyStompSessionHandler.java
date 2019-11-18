package com.cnblogs.yjmyzz.websocket.demo.javaClient;

import com.cnblogs.yjmyzz.websocket.demo.consts.GlobalConsts;
import com.cnblogs.yjmyzz.websocket.demo.model.ClientMessage;
import com.cnblogs.yjmyzz.websocket.demo.model.ServerMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Slf4j
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    //设置客户端名字
    private String name;
    private String SEND_URL;
    private String RECEIVE_URL;

    /**
     * 连接后执行的函数
     *
     * @param session
     * @param headers
     */
    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        log.info("已连接");
        //打印头文件
        showHeaders(headers);
        //设置消息订阅的地址
        subscribeTopic(this.RECEIVE_URL, session);
        //发送一条消息到地址
        sendJsonMessage(this.SEND_URL, session);
    }

    /**
     * 打印头文件
     *
     * @param headers
     */
    private void showHeaders(StompHeaders headers) {
        log.info("=========== 头文件 ===========");
        log.info(headers.toString());
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            log.info(e.getKey() + ":");
            boolean flag = true;
            for (String v : e.getValue()) {
                if (!flag) log.info(",");
                log.info(v);
                flag = false;
            }
        }
        log.info("======================");
    }

    /**
     * 发送一条消息
     *
     * @param session
     */
    private void sendJsonMessage(String send_url, StompSession session) {
        ClientMessage msg = new ClientMessage("刚连接上发送的消息是:" + name);
        session.send(send_url, msg);
    }

    /**
     * 接收订阅
     *
     * @param RECEIVE_URL
     * @param session
     */
    private void subscribeTopic(String RECEIVE_URL, StompSession session) {
        //接收订阅消息设置
        session.subscribe(RECEIVE_URL, new StompFrameHandler() {
            //设置返回消息的对象类型
            @Override
            public Type getPayloadType(StompHeaders headers) {
                showHeaders(headers);
                return ServerMessage.class;
            }

            //客户端接收消息
            //Object是接收到的对象，类型在上面getPayloadType函数设置好了。
            @Override
            public void handleFrame(StompHeaders headers, Object receiveObj) {
                showHeaders(headers);
                log.info("接收到消息:");
                log.info(receiveObj.toString());
                ServerMessage message = (ServerMessage) receiveObj;
                log.info(message.getContent());
            }
        });
    }
}
