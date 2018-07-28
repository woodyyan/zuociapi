package com.easystudio.api.zuoci.model;

public class MessageResponse {
    private MessageData data = new MessageData();

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }
}
