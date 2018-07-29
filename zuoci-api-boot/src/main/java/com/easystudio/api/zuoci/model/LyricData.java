package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Lyric data")
public class LyricData {

    @NotNull(message = "Title should not be null")
    @ApiModelProperty(value = "Lyric title", required = true)
    private String title;

    @NotNull(message = "Author ID should not be null")
    @ApiModelProperty(value = "Author Id", required = true)
    private String authorId;

    @NotNull(message = "Content should not be null")
    @ApiModelProperty(value = "Lyric content", required = true)
    private String content;

    @ApiModelProperty(value = "Lyric create location")
    private String location;

    @ApiModelProperty(value = "Lyric is visible")
    private boolean isVisible = true;

    @ApiModelProperty(value = "Lyric is valid")
    private boolean isValid = true;

    @ApiModelProperty(value = "Lyric is deleted")
    private boolean isDeleted;

    @ApiModelProperty(value = "Lyric's creative background")
    private String creativeBackground;

    @ApiModelProperty(value = "Lyric last modified time")
    private DateTime lastModifiedTime;

    @ApiModelProperty(value = "Lyric created time")
    private DateTime createdTime;

    @ApiModelProperty(value = "Lyric object id")
    private String objectId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreativeBackground() {
        return creativeBackground;
    }

    public void setCreativeBackground(String creativeBackground) {
        this.creativeBackground = creativeBackground;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
