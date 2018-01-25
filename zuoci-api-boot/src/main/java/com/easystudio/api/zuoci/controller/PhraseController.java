package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.model.PhraseResponse;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.service.PhraseService;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/phrase")
public class PhraseController {

    @Autowired
    private PhraseService service;

    @RequestMapping(method = GET)
    @ApiOperation(value = "Search phrases", notes = "Supports searching phrases based on time")
    public ResponseEntity<Phrases> searchPhrase(
            @ApiParam(value = "limit") @RequestParam(required = false) int limit) {
        Phrases response = new Phrases();
        PhraseData data = new PhraseData();
        data.setContent("content");
        data.setAuthorId(String.valueOf(limit));
        data.setLocation("chengdu");
        data.setViewCount(100L);
        data.setObjectId(123L);
        data.setCreatedTime(DateTime.now());
        data.setLastModifiedTime(DateTime.now());
        response.setData(Lists.newArrayList(data));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = POST)
    @ApiOperation(value = "Creat phraseRequest", notes = "Create phraseRequest by content")
    public ResponseEntity<?> createPhrase(@RequestBody PhraseRequest phraseRequest) {
        validate(phraseRequest);

        service.createPhrase(phraseRequest.getData());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validate(PhraseRequest phraseRequest) {

        if (isEmpty(phraseRequest.getData().getAuthorId())) {
            Error error = buildInvalidParameterError("authorId", "authorId is a required field");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (isEmpty(phraseRequest.getData().getContent())) {
            Error error = buildInvalidParameterError("content", "Content is a required field");
            throw new ErrorException(BAD_REQUEST, error);
        }
    }

    private Error buildInvalidParameterError(String parameterName, String details) {
        Error error = new Error();
        error.setStatus(BAD_REQUEST.name());
        error.setTitle("Bad request");
        error.setDetails(details);
        return error;
    }
}
