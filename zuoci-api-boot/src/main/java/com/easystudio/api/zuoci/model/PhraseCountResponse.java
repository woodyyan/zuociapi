package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonInclude(NON_ABSENT)
@ApiModel(value = "Phrase count response")
public class PhraseCountResponse {

    @ApiModelProperty(value = "Count", required = true)
    private Long count;

    @ApiModelProperty(value = "Phrase content", required = true)
    private String content;

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
