package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.DailyWordModel;
import com.easystudio.api.zuoci.service.DailyWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/dailyword", produces = "application/vnd.api+json")
@Api(tags = "Daily Word", description = "Daily Word for Zuoci")
public class DailyWordController {

    @Autowired
    private DailyWordService service;

    @RequestMapping(method = GET)
    @ApiOperation(value = "Get today word", notes = "Get today's word.")
    public ResponseEntity<DailyWordModel> getDailyWord() {
        return service.getDailyWord();
    }

    @RequestMapping(method = POST)
    @ApiOperation(value = "Create daily word", notes = "Create daily word")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createDailyWord(@RequestBody DailyWordModel dailyWord) {
        service.createDailyWord(dailyWord);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
