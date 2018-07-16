package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonInclude(NON_ABSENT)
@ApiModel(value = "Phrase response")
public class PhraseResponse {

    @ApiModelProperty(value = "Phrase data", required = true)
    private PhraseData data = new PhraseData();

    public PhraseData getData() {
        return data;
    }

    public void setData(PhraseData data) {
        this.data = data;
    }
}
