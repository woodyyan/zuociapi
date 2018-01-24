package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(value = "location", required = false, example = "北京")
    private String location;


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
}
