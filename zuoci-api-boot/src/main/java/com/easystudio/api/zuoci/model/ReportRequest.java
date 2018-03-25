package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Report Request")
public class ReportRequest {
    @ApiModelProperty(value = "Report type", required = true, example = "phrase")
    private ReportType reportType;

    @ApiModelProperty(value = "Description")
    private String description;

    @ApiModelProperty(value = "Reason")
    private String reason;

    @ApiModelProperty(value = "Reporter Id", required = true)
    private String reporterId;

    @ApiModelProperty(value = "Target Id for Phrase, Lyric, Comment", required = true)
    private int targetId;

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getTargetId() {
        return targetId;
    }
}
