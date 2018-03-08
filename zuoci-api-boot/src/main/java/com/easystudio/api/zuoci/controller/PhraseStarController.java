package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.CountResponse;
import com.easystudio.api.zuoci.model.PhraseStarRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.StarredPhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import com.easystudio.api.zuoci.validate.PhraseStarValidator;
import io.swagger.annotations.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/phrase/star", produces = "application/vnd.api+json")
@Api(tags = "Phrase Stars", description = "Phrase stars")
public class PhraseStarController {

    @Autowired
    private StarredPhraseService service;

    @Autowired
    private PhraseTranslator translator;

    @Autowired
    private PhraseStarValidator validator;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Star phrase", notes = "Star a phrase")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addStar(@RequestBody PhraseStarRequest request) throws NotFoundException {
        validator.validate(request);

        service.addStar(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = GET)
    @ApiOperation(value = "Get stars", notes = "Get stars for given user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "The index of the page requested",
                    defaultValue = "0", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "The number of elements per page",
                    defaultValue = "20", dataType = "integer", paramType = "query")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Phrases> searchStar(@ApiParam(value = "User ID")
                                              @RequestParam String userId, Pageable page) {
        Page<Phrase> pagedPhrases = service.searchStar(userId, page);
        return translator.toPhraseResponse(pagedPhrases);
    }

    @RequestMapping(value = "/{objectId}", method = DELETE)
    @ApiOperation(value = "Delete starred phrase", notes = "Delete a star from DB")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteStar(@PathVariable String userId,
                                        @PathVariable(value = "objectId") Long objectId) {
        service.deleteStar(userId, objectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/count", method = GET)
    @ApiOperation(value = "Get star count", notes = "Return star count")
    public ResponseEntity<CountResponse> countStar(@RequestParam String userId,
                                                   @RequestParam(required = false) Long phraseId) {
        Long count = service.countStar(userId, phraseId);
        CountResponse response = new CountResponse();
        response.setCount(count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
