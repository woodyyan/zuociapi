package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.ReportRequest;
import com.easystudio.api.zuoci.model.ReportType;
import com.easystudio.api.zuoci.service.ReportService;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.easymock.EasyMock.expect;

@RunWith(EasyMockRunner.class)
public class ReportControllerTest extends EasyMockSupport {

    @TestSubject
    private ReportController controller = new ReportController();

    @Mock
    private ReportService service;

    @Test
    public void shouldReportPhraseGivenPhraseId() {
        ReportRequest request = new ReportRequest();
        request.setReportType(ReportType.Phrase);

        expect(service.report(request)).andReturn(true);

        replayAll();
        ResponseEntity<?> entity = controller.report(request);
        verifyAll();

        Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        Assert.assertNull(entity.getBody());
    }

    @Test
    public void shouldGetBadRequestStatusWhenReportPhraseFailed() {
        ReportRequest request = new ReportRequest();

        expect(service.report(request)).andReturn(false);

        replayAll();
        ResponseEntity<?> entity = controller.report(request);
        verifyAll();

        Assert.assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
        Assert.assertNull(entity.getBody());
    }
}