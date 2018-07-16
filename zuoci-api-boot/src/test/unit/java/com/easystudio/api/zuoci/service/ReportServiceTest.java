package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.ReportInfo;
import com.easystudio.api.zuoci.model.ReportRequest;
import com.easystudio.api.zuoci.model.ReportType;
import com.easystudio.api.zuoci.repository.ReportRepository;
import com.easystudio.api.zuoci.translator.ReportTranslator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;

@RunWith(EasyMockRunner.class)
public class ReportServiceTest extends EasyMockSupport {

    @TestSubject
    private ReportService service = new ReportService();

    @Mock
    private ReportRepository repository;

    @Mock
    private ReportTranslator translator;

    @Test
    public void shouldReportGivenRequest() {
        ReportRequest request = new ReportRequest();
        request.setDescription("");
        request.setReason("");
        request.setReporterId("");
        request.setReportType(ReportType.Phrase);
        request.setTargetId("1");

        ReportInfo report = new ReportInfo();
        report.setDescription(request.getDescription());
        report.setReason(request.getReason());
        report.setReporterId(request.getReporterId());
        report.setReportType(request.getReportType());
        report.setTargetId(request.getTargetId());

        ReportInfo result = new ReportInfo();
        expect(translator.translateReport(request)).andReturn(report);
        expect(repository.save(report)).andReturn(result);

        replayAll();
        boolean success = service.report(request);
        verifyAll();

        Assert.assertTrue(success);
    }

    @Test
    public void shouldReturnFalseGivenSaveFailed() {
        ReportRequest request = new ReportRequest();
        request.setDescription("");
        request.setReason("");
        request.setReporterId("");
        request.setReportType(ReportType.Phrase);
        request.setTargetId("1");

        ReportInfo report = new ReportInfo();
        report.setDescription(request.getDescription());
        report.setReason(request.getReason());
        report.setReporterId(request.getReporterId());
        report.setReportType(request.getReportType());
        report.setTargetId(request.getTargetId());

        expect(translator.translateReport(request)).andReturn(report);
        expect(repository.save(report)).andReturn(null);

        replayAll();
        boolean success = service.report(request);
        verifyAll();

        Assert.assertFalse(success);
    }
}