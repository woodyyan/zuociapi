package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Lyric;
import com.easystudio.api.zuoci.model.LyricData;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.easystudio.api.zuoci.repository.LyricRepository;
import com.easystudio.api.zuoci.translator.LyricTranslator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LyricServiceTest {
    @InjectMocks
    private LyricService service;

    @Mock
    private LyricTranslator translator;

    @Mock
    private LyricRepository repository;

    @Test
    public void shouldReturnLyricResponseWhenCreateLyricSuccessfully() {
        LyricData data = new LyricData();
        Lyric lyric = new Lyric();
        Lyric savedLyric = new Lyric();
        LyricResponse response = new LyricResponse();
        response.getData().setContent("content");
        response.getData().setTitle("title");
        response.getData().setObjectId("id");

        when(translator.toLyricEntity(data)).thenReturn(lyric);
        when(repository.save(lyric)).thenReturn(savedLyric);
        when(translator.toLyricResponse(savedLyric)).thenReturn(response);

        LyricResponse lyricResponse = service.createLyric(data);

        Assert.assertEquals("content", lyricResponse.getData().getContent());
        Assert.assertEquals("title", lyricResponse.getData().getTitle());
        Assert.assertEquals("id", lyricResponse.getData().getObjectId());
    }
}