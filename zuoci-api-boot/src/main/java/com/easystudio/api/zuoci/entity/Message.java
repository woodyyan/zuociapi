package com.easystudio.api.zuoci.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    //推送文字
    @Column(name = "content", nullable = false)
    private String content;

    //客户端显示的文字
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "receiver_id", nullable = false)
    private String receiverId;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "lyric_id")
    private String lyricId;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "last_modified_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime", parameters = {
            @org.hibernate.annotations.Parameter(name = "databaseZone", value = "Asia/Shanghai")
    })
    private LocalDateTime lastModifiedTime;

    @Column(name = "created_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime", parameters = {
            @org.hibernate.annotations.Parameter(name = "databaseZone", value = "Asia/Shanghai")
    })
    private LocalDateTime createdTime;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

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

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
