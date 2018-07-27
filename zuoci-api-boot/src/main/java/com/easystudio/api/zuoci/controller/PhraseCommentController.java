package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.CountResponse;
import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import com.easystudio.api.zuoci.model.PhraseComments;
import com.easystudio.api.zuoci.service.PhraseCommentService;
import com.easystudio.api.zuoci.validate.PhraseCommentValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/phraseComment", produces = "application/vnd.api+json")
@Api(tags = "Phrase Comment", description = "Operations on Phrase Comment")
public class PhraseCommentController {

    @Autowired
    private PhraseCommentValidator validator;

    @Autowired
    private PhraseCommentService service;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create phrase comment", notes = "Create phrase comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CommentData> createComment(@RequestBody PhraseCommentRequest phraseCommentRequest) {
        validator.validate(phraseCommentRequest);

        CommentData comment = service.createComment(phraseCommentRequest.getData());

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET)
    @ApiOperation(value = "Search phrase comment", notes = "Search phrase comment")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "The index of the page requested",
                    defaultValue = "0", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "The number of elements per page",
                    defaultValue = "20", dataType = "integer", paramType = "query")
    })
    public ResponseEntity<PhraseComments> searchComment(@ApiParam(value = "Phrase id")
                                                        @RequestParam(value = "phraseId", required = false) Long phraseId,
                                                        @ApiParam(value = "User id")
                                                        @RequestParam(value = "userId", required = false) String userId,
                                                        @ApiParam(value = "Phrase is visible", defaultValue = "true")
                                                        @RequestParam(required = false, defaultValue = "true") boolean isVisible,
                                                        Pageable page) {
        PhraseComments phraseComments = service.searchComment(phraseId, userId, isVisible, page);
        return new ResponseEntity<>(phraseComments, HttpStatus.OK);
    }

    @RequestMapping(value = "/{objectId}", method = GET)
    @ApiOperation(value = "Get Comment", notes = "Get a phrase comment by id")
    public ResponseEntity<CommentData> getComment(
            @ApiParam(value = "Phrase id")
            @PathVariable Long objectId) {

        CommentData commentData = service.getComment(objectId);
        return new ResponseEntity<>(commentData, HttpStatus.OK);
    }

    @RequestMapping(value = "/count", method = GET)
    @ApiOperation(value = "Get phrase comment count", notes = "Return phrase comment count")
    public ResponseEntity<CountResponse> countComment(@ApiParam(value = "Phrase id")
                                                      @RequestParam(value = "phraseId", required = false) Long phraseId,
                                                      @ApiParam(value = "User id")
                                                      @RequestParam(value = "userId", required = false) String userId,
                                                      @ApiParam(value = "Phrase is visible", defaultValue = "true")
                                                      @RequestParam(required = false, defaultValue = "true")
                                                              boolean isVisible) {
        Long count = service.countComment(phraseId, userId, isVisible);
        CountResponse response = new CountResponse();
        response.setCount(count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{commentId}", method = DELETE)
    @ApiOperation(value = "Delete phrase comment", notes = "Delete a phrase comment from DB")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId) {
        service.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
