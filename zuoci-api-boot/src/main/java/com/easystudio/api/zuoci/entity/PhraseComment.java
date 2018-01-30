package com.easystudio.api.zuoci.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "phrase_comment")
public class PhraseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "commentator_id", nullable = false)
    private String commentatorId;

    @Column(name = "created_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lastModifiedTime;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "phrase_author_id", nullable = false)
    private String phraseAuthorId;

    @Column(name = "phrase_id", nullable = false)
    private Long phraseId;

    @Column(name = "replied_user_id")
    private String repliedUserId;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCommentatorId(String commentatorId) {
        this.commentatorId = commentatorId;
    }

    public String getCommentatorId() {
        return commentatorId;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setPhraseAuthorId(String phraseAuthorId) {
        this.phraseAuthorId = phraseAuthorId;
    }

    public String getPhraseAuthorId() {
        return phraseAuthorId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public Long getPhraseId() {
        return phraseId;
    }

    public void setRepliedUserId(String repliedUserId) {
        this.repliedUserId = repliedUserId;
    }

    public String getRepliedUserId() {
        return repliedUserId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
