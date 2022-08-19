package com.xc.springevent.event.spring.template;

public class RegisterMessageEvent extends BaseEvent {

    private String msgId;

    public RegisterMessageEvent(String msgId) {
        super();
        this.msgId = msgId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
