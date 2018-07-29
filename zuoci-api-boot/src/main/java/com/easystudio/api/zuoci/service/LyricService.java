package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Lyric;
import com.easystudio.api.zuoci.model.LyricData;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.easystudio.api.zuoci.repository.LyricRepository;
import com.easystudio.api.zuoci.translator.LyricTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LyricService {
    @Autowired
    private LyricRepository repository;

    @Autowired
    private LyricTranslator translator;

    public LyricResponse createLyric(LyricData data) {
        Lyric lyric = translator.toLyricEntity(data);
        Lyric savedLyric = repository.save(lyric);
        return translator.toLyricResponse(savedLyric);
    }
}
