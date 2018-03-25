package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.ReportInfo;
import com.easystudio.api.zuoci.model.ReportRequest;
import com.easystudio.api.zuoci.repository.ReportRepository;
import com.easystudio.api.zuoci.translator.ReportTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportTranslator translator;

    public boolean report(ReportRequest request) {
        ReportInfo report = translator.translateReport(request);
        ReportInfo result = repository.save(report);
        return result != null;
    }
}
