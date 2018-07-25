package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Message data")
public class MessageData {

    //记录推送文案
    @NotNull(message = "content is a required field")
    @ApiModelProperty(value = "Content", required = true)
    private String content;

    //客户端显示的文字
    @NotNull(message = "text is a required field")
    @ApiModelProperty(value = "Text", required = true)
    private String text;

    private String receiverId;
    private String senderId;
    private String channel;
    private String lyricId;
    private String commentId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLyricId() {
        return lyricId;
    }

    public void setLyricId(String lyricId) {
        this.lyricId = lyricId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
