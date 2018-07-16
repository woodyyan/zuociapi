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
    private ResonanceLike resonanceLike;

    @ApiModelProperty(value = "Phrase interesting like", required = true)
    private InterestingLike interestingLike;

    @ApiModelProperty(value = "Phrase feeling like", required = true)
    private InspirationLike inspirationLike;

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public ResonanceLike getResonanceLike() {
        return resonanceLike;
    }

    public void setResonanceLike(ResonanceLike resonanceLike) {
        this.resonanceLike = resonanceLike;
    }

    public InterestingLike getInterestingLike() {
        return interestingLike;
    }

    public void setInterestingLike(InterestingLike interestingLike) {
        this.interestingLike = interestingLike;
    }

    public InspirationLike getInspirationLike() {
        return inspirationLike;
    }

    public void setInspirationLike(InspirationLike inspirationLike) {
        this.inspirationLike = inspirationLike;
    }
}
