package com.easystudio.api.zuoci.model.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status","title","details"})
public class Error {
    @ApiModelProperty(value = "HTTP status", example = "400", required = true)
    private String status;

    @ApiModelProperty(value = "Error title", example = "Bad request", required = true)
    private String title;

    @ApiModelProperty(value = "Error details", example = "A field is invalid")
    private String details;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
