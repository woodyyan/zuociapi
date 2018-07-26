package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
@ApiModel(value = "Message request")
public class MessageRequest {
    //没有receiver的就是推荐歌词，就表示所有人都会接收到
    @Valid
    @ApiModelProperty(value = "Message data", required = true)
    private MessageData data = new MessageData();

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }
}
