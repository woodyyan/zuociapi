package com.easystudio.api.zuoci.model;

import com.easystudio.api.zuoci.entity.Phrase;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;


@JsonInclude(NON_ABSENT)
@ApiModel(value = "Phrase response")
public class Phrases {

    @ApiModelProperty(value = "Phrase data", required = true)
    private List<PhraseData> data = new ArrayList<>();

    public List<PhraseData> getData() {
        return data;
    }

    public void setData(List<PhraseData> data) {
        this.data = data;
    }
}