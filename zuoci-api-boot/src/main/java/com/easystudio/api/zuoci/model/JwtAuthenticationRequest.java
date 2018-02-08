package com.easystudio.api.zuoci.model;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String appKey;
    private String appSecret;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String appKey, String appSecret) {
        this.setAppKey(appKey);
        this.setAppSecret(appSecret);
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
