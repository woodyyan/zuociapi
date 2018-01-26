package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.service.PhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/phrase", produces = "application/vnd.api+json")
@Api(tags = "Phrase Resource", description = "Operations on Customer")
public class PhraseController {

    @Autowired
    private PhraseService service;

    @Autowired
    private PhraseTranslator translator;

    @RequestMapping(method = GET)
    @ApiOperation(value = "Search phrases", notes = "Supports searching phrases based on time")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "The index of the page requested", defaultValue = "0", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "The number of elements per page", defaultValue = "20", dataType = "integer", paramType = "query")
    })
    public ResponseEntity<Phrases> searchPhrase(Pageable page) {
        Page<Phrase> pagedPhrases = service.searchPhrase(page);
        return translator.toPhraseResponse(pagedPhrases);
    }

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create phraseRequest", notes = "Create phraseRequest by content")
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
