package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.DailyWord;
import com.easystudio.api.zuoci.model.DailyWordModel;
import com.easystudio.api.zuoci.repository.DailyWordRepository;
import com.easystudio.api.zuoci.translator.DailyWordTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DailyWordService {

    @Autowired
    private DailyWordRepository repository;

    @Autowired
    private DailyWordTranslator translator;

    public ResponseEntity<DailyWordModel> getDailyWord() {
        DailyWord dailyWord = repository.findFirstByOrderByCreatedTimeDesc();
        DailyWordModel response = translator.toDailyWordResponse(dailyWord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void createDailyWord(DailyWordModel dailyWordModel) {
        DailyWord dailyWord = translator.toDailyWordEntity(dailyWordModel);
        repository.save(dailyWord);
    }
}
