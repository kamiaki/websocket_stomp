package com.cnblogs.yjmyzz.websocket.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author junmingyang
 * 收发的消息类，必须存在"无参的默认构造函数"，否则topic订阅会出问题，而且代码不报错！
 */
@Data   //get set tostring EqualsAndHashCode
@AllArgsConstructor // 全参构造函数
public class ClientMessage {

    private String name;

    public ClientMessage() {
    }

}
