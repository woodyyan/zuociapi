package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.LyricRequest;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.easystudio.api.zuoci.service.LyricService;
import com.easystudio.api.zuoci.validate.LyricValidator;
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
@RequestMapping(value = "/lyric", produces = "application/vnd.api+json")
@Api(tags = "Lyric", description = "Operations on Lyric")
public class LyricController {

    @Autowired
    private LyricValidator validator;

    @Autowired
    private LyricService service;

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create lyric", notes = "Create lyric")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<LyricResponse> createLyric(@RequestBody LyricRequest lyricRequest) {
        validator.validate(lyricRequest);

        LyricResponse response = service.createLyric(lyricRequest.getData());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
