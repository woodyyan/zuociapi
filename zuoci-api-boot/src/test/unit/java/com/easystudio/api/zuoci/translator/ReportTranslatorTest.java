package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.ReportInfo;
import com.easystudio.api.zuoci.model.ReportRequest;
import com.easystudio.api.zuoci.model.ReportType;
import org.junit.Assert;
import org.junit.Test;

public class ReportTranslatorTest {
    private ReportTranslator translator = new ReportTranslator();

    @Test
    public void shouldTranslateReportRequestToReportInfo() {
        ReportRequest request = new ReportRequest();
        request.setTargetId("5555");
        request.setReportType(ReportType.Phrase);
        request.setReporterId("123");
        request.setReason("reason");
        request.setDescription("description");

        ReportInfo reportInfo = translator.translateReport(request);

        Assert.assertEquals("description", reportInfo.getDescription());
        Assert.assertEquals("reason", reportInfo.getReason());
        Assert.assertEquals("123", reportInfo.getReporterId());
        Assert.assertEquals(ReportType.Phrase, reportInfo.getReportType());
        Assert.assertEquals("5555", reportInfo.getTargetId());
        Assert.assertNotNull(reportInfo.getCreatedTime());
        Assert.assertNull(reportInfo.getObjectId());
    }
}