package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonInclude(NON_ABSENT)
@ApiModel(value = "Count")
public class CountResponse {
    @ApiModelProperty(value = "Count", example = "1")
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
