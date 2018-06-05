package com.easystudio.api.zuoci.entity;

import com.easystudio.api.zuoci.model.ReportType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "report_info")
public class ReportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Column(name = "description")
    private String description;

    @Column(name = "reason")
    private String reason;

    @Column(name = "reporter_id", nullable = false)
    private String reporterId;

    @Column(name = "report_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(name = "target_id", nullable = false)
    private String targetId;

    @Column(name = "created_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime", parameters = {
            @org.hibernate.annotations.Parameter(name = "databaseZone", value = "Asia/Shanghai")
    })
    private LocalDateTime createdTime;

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

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetId() {
        return targetId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
