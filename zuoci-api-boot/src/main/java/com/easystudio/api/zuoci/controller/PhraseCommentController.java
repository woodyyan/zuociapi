package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import com.easystudio.api.zuoci.model.PhraseComments;
import com.easystudio.api.zuoci.service.PhraseCommentService;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import com.easystudio.api.zuoci.validate.PhraseCommentValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/phrase/comment", produces = "application/vnd.api+json")
@Api(tags = "Phrase Comment", description = "Operations on Phrase Comment")
public class PhraseCommentController {

    @Autowired
    private PhraseCommentValidator validator;

    @Autowired
    private PhraseCommentService service;

    @Autowired
    private PhraseCommentTranslator translator;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create phrase comment", notes = "Create phrase comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createComment(@RequestBody PhraseCommentRequest phraseCommentRequest) {
        validator.validate(phraseCommentRequest);

        service.createComment(phraseCommentRequest.getData());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = GET)
    @ApiOperation(value = "Search phrase comment", notes = "Search phrase comment")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PhraseComments> searchComment(Pageable page) {
        Page<PhraseComment> pagedComments = service.searchComment(page);
        return translator.toPhraseCommentResponse(pagedComments);
    }

    @RequestMapping(value = "/{objectId}", method = DELETE)
    @ApiOperation(value = "Delete phrase comment", notes = "Delete a phrase comment from DB")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteComment(@PathVariable(value = "objectId") Long objectId) {
        service.deleteComment(objectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
