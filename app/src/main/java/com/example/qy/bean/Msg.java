package com.example.qy.bean;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 04 日 上午 11:17
 * Description: 消息Bean
 */
public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;
    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }
    public String getContent(){
        return content;
    }
    public int getType(){
        return type;
    }
}
