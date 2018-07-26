package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.service.MessageService;
import com.easystudio.api.zuoci.validate.MessageValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/message", produces = "application/vnd.api+json")
@Api(tags = "Message", description = "Message Box")
public class MessageController {
    @Autowired
    private MessageService service;

    @Autowired
    private MessageValidator validator;

    @RequestMapping(method = GET)
    @ApiOperation(value = "Search message", notes = "Search message by receiver id")
    public ResponseEntity<Messages> searchMessage(@ApiParam(value = "Receiver Id")
                                                  @RequestParam(required = false) String receiverId,
                                                  Pageable page) {
        Messages messages = service.searchMessage(receiverId, page);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create message", notes = "Create message")
    public ResponseEntity<?> createMessage(@RequestBody MessageRequest messageRequest) {
        validator.validate(messageRequest);
        service.createMessage(messageRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
