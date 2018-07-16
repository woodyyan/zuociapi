package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Phrase Comment Request")
public class PhraseCommentRequest {

    @Valid
    @ApiModelProperty(value = "Phrase comment data", required = true)
    private CommentData data = new CommentData();

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }
}