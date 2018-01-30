package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.service.PhraseLikeService;
import com.easystudio.api.zuoci.validate.PhraseLikeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/phrase/like", produces = "application/vnd.api+json")
@Api(tags = "Phrase like", description = "Like for Phrase")
public class PhraseLikeController {

    @Autowired
    private PhraseLikeValidator validator;

    @Autowired
    private PhraseLikeService service;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Add phrase like", notes = "Add phrase like")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addLike(@RequestBody PhraseLikeRequest phraseLikeRequest) {
        validator.validate(phraseLikeRequest);

        service.addLike(phraseLikeRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
