package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LyricRequest {
    private LyricData data = new LyricData();

    public LyricData getData() {
        return data;
    }

    public void setData(LyricData data) {
        this.data = data;
    }
}
