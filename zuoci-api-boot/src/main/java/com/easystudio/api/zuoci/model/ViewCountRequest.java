package com.easystudio.api.zuoci.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonInclude(NON_ABSENT)
@ApiModel(value = "Phrase view count")
public class ViewCountRequest {

    @ApiModelProperty(value = "View count operation", required = true)
    private ViewCountOperation operation;

    @ApiModelProperty(value = "View count amount", required = true)
    private Long amount;

    public ViewCountOperation getOperation() {
        return operation;
    }

    public void setOperation(ViewCountOperation operation) {
        this.operation = operation;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
