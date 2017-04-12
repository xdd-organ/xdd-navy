package com.java.xdd.websocket1.domain;

import com.java.xdd.common.domain.BaseDomain;

public class Message extends BaseDomain{

    private static final long serialVersionUID = -6377484609102682072L;
    private Long id;//消息id
    private String content;//消息内容
    private Long from;//谁发送的
    private Long sendTo;//发送给谁
    private Boolean isRead;//是否已读
    private String type;//消息类型

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getSendTo() {
        return sendTo;
    }

    public void setSendTo(Long sendTo) {
        this.sendTo = sendTo;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
