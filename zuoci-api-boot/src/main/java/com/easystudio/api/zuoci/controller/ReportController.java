package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.ReportRequest;
import com.easystudio.api.zuoci.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/report", produces = "application/vnd.api+json")
@Api(tags = "Report", description = "Report lyric, phrase and comment")
public class ReportController {

    @Autowired
    private ReportService service;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create report", notes = "Create report by request")
    public ResponseEntity<?> report(@RequestBody ReportRequest request) {
        boolean success = service.report(request);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
