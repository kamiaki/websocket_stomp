package com.cnblogs.yjmyzz.websocket.demo.controller;

import com.cnblogs.yjmyzz.websocket.demo.consts.GlobalConsts;
import com.cnblogs.yjmyzz.websocket.demo.model.ClientMessage;
import com.cnblogs.yjmyzz.websocket.demo.model.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author junmingyang
 * @date 2018/9/24 7:03 PM
 */
@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping(GlobalConsts.send_url1)
    @SendTo(GlobalConsts.main_receive_url + GlobalConsts.receive_url1)
    public ServerMessage greeting(ClientMessage message) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(1000);
        return new ServerMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    /**
     * 最基本的服务器端主动推送消息给前端
     *
     * @return
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000)
    public String serverTime() {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend(
                GlobalConsts.main_receive_url + GlobalConsts.receive_url2,
                df.format(new Date())
        );
        return "servertime";
    }

    /**
     * 以下面这种方式发送消息，前端订阅消息的方式为： 用户订阅
     *
     * @return
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000)
    public String serverTimeToUser() {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSendToUser(
                GlobalConsts.userTestName,
                GlobalConsts.userUrl,
                df.format(new Date())
        );
        return "serverTimeToUser";
    }
}
