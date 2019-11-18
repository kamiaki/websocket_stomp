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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author wangzhe
 * @date 2019 11 18
 */
@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping(GlobalConsts.send_url1)//客户端发送地址
    @SendTo(GlobalConsts.main_receive_url + GlobalConsts.receive_url1)//客户端订阅地址
    public ServerMessage greeting(ClientMessage message) throws Exception {
        //模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(1000);
        //格式转换为ISO-8859-1，防止乱码
        String msg = HtmlUtils.htmlEscape(message.getName());
        return new ServerMessage("后台返回消息:" + msg);
    }

    /**
     * 最基本的服务器端主动推送消息给前端
     * 返回值也可以是string
     *
     * @return
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000)
    public void serverTime() {
        // 发现消息
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = "最基本的服务器端主动推送消息给前端，内容是发送时间:" + dateStr;
        String RECEIVE_URL = GlobalConsts.main_receive_url + GlobalConsts.receive_url2;
        messagingTemplate.convertAndSend(RECEIVE_URL, msg);
        return;
    }

    /**
     * 以下面这种方式发送消息，前端订阅消息的方式为： 用户订阅
     * 返回值也可以是string
     *
     * @return
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000)
    public void serverTimeToUser() {
        // 发现消息
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = "以下面这种方式发送消息，前端订阅消息的方式为： 用户订阅，内容是发送时间:" + dateStr;
        messagingTemplate.convertAndSendToUser(
                GlobalConsts.userTestName,
                GlobalConsts.userUrl,
                msg
        );
        return;
    }
}
