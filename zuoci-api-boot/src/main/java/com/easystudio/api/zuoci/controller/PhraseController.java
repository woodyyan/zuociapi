package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.PhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import com.easystudio.api.zuoci.validate.PhraseValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/phrase", produces = "application/vnd.api+json")
@Api(tags = "Phrase Resource", description = "Operations on Phrase")
public class PhraseController {

    @Autowired
    private PhraseService service;

    @Autowired
    private PhraseTranslator translator;

    @Autowired
    private PhraseValidator validator;

    @RequestMapping(method = GET)
    @ApiOperation(value = "Search phrases", notes = "Supports searching phrases based on time")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "The index of the page requested", defaultValue = "0", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "The number of elements per page", defaultValue = "20", dataType = "integer", paramType = "query")
    })
    public ResponseEntity<Phrases> searchPhrase(
            @ApiParam(value = "Phrase is valid", defaultValue = "true")
            @RequestParam(required = false, defaultValue = "true") boolean isValid,
            @ApiParam(value = "Phrase is visible")
            @RequestParam(required = false, defaultValue = "true") boolean isVisible, Pageable page) {

        Page<Phrase> pagedPhrases = service.searchPhrase(isValid, isVisible, page);
        return translator.toPhraseResponse(pagedPhrases);
    }

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create phraseRequest", notes = "Create phraseRequest by content")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createPhrase(@RequestBody PhraseRequest phraseRequest) {
        validator.validate(phraseRequest);

        service.createPhrase(phraseRequest.getData());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{objectId}", method = DELETE)
    @ApiOperation(value = "Delete phrase", notes = "Delete a phrase from DB")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deletePhrase(@PathVariable(value = "objectId") Long objectId) {
        service.deletePhrase(objectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
