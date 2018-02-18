package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonInclude(NON_ABSENT)
@ApiModel(value = "Phrase comment response")
public class PhraseComments {

    @ApiModelProperty(value = "Phrase comment data", required = true)
    private List<CommentData> data = new ArrayList<>();

    @ApiModelProperty(value = "Pagination information", required = true, readOnly = true)
    private PagingMeta meta = new PagingMeta();

    public List<CommentData> getData() {
        return data;
    }

    public void setData(List<CommentData> data) {
        this.data = data;
    }

    public PagingMeta getMeta() {
        return meta;
    }

    public void setMeta(PagingMeta meta) {
        this.meta = meta;
    }
}