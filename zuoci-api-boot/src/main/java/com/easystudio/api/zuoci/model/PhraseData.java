package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Phrase data")
public class PhraseData {

    @NotNull(message = "Author id is a required field")
    @ApiModelProperty(value = "Author ID", required = true, example = "55f95a6360b232fc2aa1f4ca")
    private String authorId;

    @NotNull(message = "Content is a required field")
    @ApiModelProperty(value = "Content", required = true, example = "")
    private String content;

    @ApiModelProperty(value = "Location", example = "beijing")
    private String location;

    @ApiModelProperty(value = "Object Id", example = "123")
    private Long objectId;

    @ApiModelProperty(value = "View Count", example = "100")
    private Long viewCount;

    @ApiModelProperty(value = "Last Modified Time")
    @JsonFormat(timezone = "Asia/Shanghai")
    private DateTime lastModifiedTime;

    @ApiModelProperty(value = "Created Time")
    @JsonFormat(timezone = "Asia/Shanghai")
    private DateTime createdTime;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public DateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(DateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public DateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }
}
