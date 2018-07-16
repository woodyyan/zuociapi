package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Phrase Request")
public class PhraseRequest {

    @Valid
    @ApiModelProperty(value = "Phrase data", required = true)
    private PhraseData data = new PhraseData();

    public PhraseData getData() {
        return data;
    }

    public void setData(PhraseData data) {
        this.data = data;
    }
}
