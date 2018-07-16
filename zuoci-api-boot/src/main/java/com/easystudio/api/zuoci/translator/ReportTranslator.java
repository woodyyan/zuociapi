package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.ReportInfo;
import com.easystudio.api.zuoci.model.ReportRequest;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ReportTranslator {
    public ReportInfo translateReport(ReportRequest request) {
        ReportInfo reportInfo = new ReportInfo();
        reportInfo.setDescription(request.getDescription());
        reportInfo.setReason(request.getReason());
        reportInfo.setReporterId(request.getReporterId());
        reportInfo.setReportType(request.getReportType());
        reportInfo.setTargetId(request.getTargetId());
        reportInfo.setCreatedTime(LocalDateTime.now());
        return reportInfo;
    }
}
