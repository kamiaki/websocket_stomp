package com.cnblogs.yjmyzz.websocket.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author junmingyang
 * 收发的消息类，必须存在"无参的默认构造函数"，否则topic订阅会出问题，而且代码不报错！
 */
@Data
@AllArgsConstructor
public class ServerMessage {

    private String content;

    public ServerMessage() {
    }

    @Override
    public String toString() {
        return content;
    }

}
