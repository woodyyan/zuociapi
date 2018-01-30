package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Phrase Like Response")
public class PhraseLikeResponse {

    @ApiModelProperty(value = "Phrase Id", required = true)
    private Long phraseId;

    @ApiModelProperty(value = "Phrase normal like", required = true)
    private NormalLike normalLike;

    @ApiModelProperty(value = "Phrase interesting like", required = true)
    private InterestingLike interestingLike;

    @ApiModelProperty(value = "Phrase feeling like", required = true)
    private FeelingLike feelingLike;

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public NormalLike getNormalLike() {
        return normalLike;
    }

    public void setNormalLike(NormalLike normalLike) {
        this.normalLike = normalLike;
    }

    public InterestingLike getInterestingLike() {
        return interestingLike;
    }

    public void setInterestingLike(InterestingLike interestingLike) {
        this.interestingLike = interestingLike;
    }

    public FeelingLike getFeelingLike() {
        return feelingLike;
    }

    public void setFeelingLike(FeelingLike feelingLike) {
        this.feelingLike = feelingLike;
    }
}
