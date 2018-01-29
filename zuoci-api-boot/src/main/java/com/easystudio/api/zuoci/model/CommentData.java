package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Phrase comment data")
public class CommentData {
    @NotNull(message = "Phrase Author id is a required field")
    @ApiModelProperty(value = "Phrase Author ID", required = true, example = "55f95a6360b232fc2aa1f4ca")
    private String phraseAuthorId;

    @ApiModelProperty(value = "Phrase Comment ID", example = "123")
    private Long objectId;

    @ApiModelProperty(value = "Parent Comment ID", example = "123")
    private Long parentCommentId;

    @NotNull(message = "Comment content is a required field")
    @ApiModelProperty(value = "Comment content", required = true)
    private String content;

    @NotNull(message = "Phrase Id is a required field")
    @ApiModelProperty(value = "Phrase ID", required = true, example = "123")
    private Long phraseId;

    @NotNull(message = "Parent Id is a required field")
    @ApiModelProperty(value = "Parent ID", required = true)
    private Long parentId;

    @NotNull(message = "Commentator Id is a required field")
    @ApiModelProperty(value = "Commentator ID", required = true, example = "55f95a6360b232fc2aa1f4ca")
    private String commentatorId;

    @ApiModelProperty(value = "Replied User ID")
    private String repliedUserId;

    @ApiModelProperty(value = "Created Time")
    private DateTime createdTime;

    @ApiModelProperty(value = "Last Modified Time")
    private DateTime lastModifiedTime;

    public String getPhraseAuthorId() {
        return phraseAuthorId;
    }

    public void setPhraseAuthorId(String phraseAuthorId) {
        this.phraseAuthorId = phraseAuthorId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(String commentatorId) {
        this.commentatorId = commentatorId;
    }

    public String getRepliedUserId() {
        return repliedUserId;
    }

    public void setRepliedUserId(String repliedUserId) {
        this.repliedUserId = repliedUserId;
    }

    public DateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    public DateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(DateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
