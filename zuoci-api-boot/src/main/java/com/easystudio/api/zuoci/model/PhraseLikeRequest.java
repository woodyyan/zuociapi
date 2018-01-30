package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Phrase Like Request")
public class PhraseLikeRequest {

    @ApiModelProperty(value = "Phrase like user id", required = true)
    private String userId;

    @ApiModelProperty(value = "Phrase id", required = true)
    private Long phraseId;

    @ApiModelProperty(value = "Phrase like type", required = true)
    private LikeType likeType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public LikeType getLikeType() {
        return likeType;
    }

    public void setLikeType(LikeType likeType) {
        this.likeType = likeType;
    }
}