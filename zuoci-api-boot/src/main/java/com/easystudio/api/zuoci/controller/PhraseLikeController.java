package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.PhraseLikeResponse;
import com.easystudio.api.zuoci.service.PhraseLikeService;
import com.easystudio.api.zuoci.validate.PhraseLikeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/phrase/{phraseId}/like", produces = "application/vnd.api+json")
@Api(tags = "Phrase like", description = "Like for Phrase")
public class PhraseLikeController {

    @Autowired
    private PhraseLikeValidator validator;

    @Autowired
    private PhraseLikeService service;

    @RequestMapping(method = POST, consumes = "application/vnd.api+json")
    @ApiOperation(value = "Add phrase like", notes = "Add phrase like")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addLike(@PathVariable Long phraseId,
                                     @RequestBody PhraseLikeRequest phraseLikeRequest) {
        validator.validate(phraseId, phraseLikeRequest);

        service.addLike(phraseLikeRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = GET)
    @ApiOperation(value = "Get phrase like count", notes = "Get phrase like count")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PhraseLikeResponse> getLikeCount(@PathVariable Long phraseId) {
        return service.getLikeCount(phraseId);
    }
}