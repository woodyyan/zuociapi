package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.StarredPhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/star/user/{userId}", produces = "application/vnd.api+json")
@Api(tags = "Starred Phrase", description = "Star a Phrase")
public class StarredPhraseController {

    @Autowired
    private StarredPhraseService service;
    private PhraseTranslator translator;

    @RequestMapping(value = "/phrase/{phraseId}", method = POST)
    @ApiOperation(value = "Star phrase", notes = "Star a phrase")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addStar(@PathVariable Long phraseId,
                                     @PathVariable String userId) throws NotFoundException {
        service.addStar(phraseId, userId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = GET)
    @ApiOperation(value = "Get star", notes = "Get star for given user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "The index of the page requested",
                    defaultValue = "0", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "The number of elements per page",
                    defaultValue = "20", dataType = "integer", paramType = "query")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Phrases> searchStar(@PathVariable String userId, Pageable page) {
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
}
