package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
@ApiModel(value = "Message response")
public class Messages {

    @ApiModelProperty(value = "Message data", required = true)
    private List<MessageData> data = new ArrayList<>();

    @ApiModelProperty(value = "Pagination information", required = true, readOnly = true)
    private PagingMeta meta = new PagingMeta();

    public List<MessageData> getData() {
        return data;
    }

    public void setData(List<MessageData> data) {
        this.data = data;
    }

    public PagingMeta getMeta() {
        return meta;
    }

    public void setMeta(PagingMeta meta) {
        this.meta = meta;
    }
}
