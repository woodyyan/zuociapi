package com.easystudio.api.zuoci.controller;


import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import com.easystudio.api.zuoci.service.PhraseCommentService;
import com.easystudio.api.zuoci.validate.PhraseCommentValidator;
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
@RequestMapping(value = "/phrase/comment", produces = "application/vnd.api+json")
@Api(tags = "Phrase Comment", description = "Operations on Phrase Comment")
public class PhraseCommentController {

    @Autowired
    private PhraseCommentValidator validator;

    @Autowired
    private PhraseCommentService service;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create phrase comment", notes = "Create phrase comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createComment(@RequestBody PhraseCommentRequest phraseCommentRequest) {
        validator.validate(phraseCommentRequest);

        service.createComment(phraseCommentRequest.getData());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
