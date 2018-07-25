package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.service.MessageService;
import com.easystudio.api.zuoci.translator.MessageTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/message", produces = "application/vnd.api+json")
@Api(tags = "Message", description = "Message Box")
public class MessageController {
    @Autowired
    private MessageService service;

    @Autowired
    private MessageTranslator translator;

    @RequestMapping(method = GET)
    @ApiOperation(value = "Get today word", notes = "Get today's word.")
    public ResponseEntity<Messages> searchMessage(@ApiParam(value = "Receiver Id")
                                                  @RequestParam(required = false) String receiverId,
                                                  Pageable page) {
        Page<Message> pagedMessages = service.searchMessage(receiverId, page);
        return translator.toMessageResponse(pagedMessages);
    }
}
