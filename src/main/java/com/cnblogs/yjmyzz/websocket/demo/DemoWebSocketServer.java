package com.cnblogs.yjmyzz.websocket.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author junmingyang
 * 这个类没啥好说的，唯一注意的是，实际调试中发现，
 * 这个类的package位置，最好放在"最外"层，移到子package后，client客户端会连接不上。
 * （应该是要同步修改其它地方）
 */
@SpringBootApplication
@EnableScheduling   //定时任务
public class DemoWebSocketServer {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebSocketServer.class, args);
    }
}
