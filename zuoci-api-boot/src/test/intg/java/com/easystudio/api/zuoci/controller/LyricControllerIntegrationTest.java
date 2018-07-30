package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Lyric;
import com.easystudio.api.zuoci.model.LyricRequest;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.easystudio.api.zuoci.repository.LyricRepository;
import com.google.common.base.Strings;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

public class LyricControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private LyricRepository repository;

    @Test
    public void shouldCreateLyricSuccessfully() throws Exception {
        String url = "/lyric";
        String background = "背景";
        String content = "content";
        String title = "title";
        String location = "成都";
        String authorId = "123";

        LyricRequest lyricRequest = new LyricRequest();
        lyricRequest.getData().setTitle(title);
        lyricRequest.getData().setContent(content);
        lyricRequest.getData().setLocation(location);
        lyricRequest.getData().setCreativeBackground(background);
        lyricRequest.getData().setAuthorId(authorId);

        MockHttpServletResponse response = performPostRequest(url, lyricRequest);

        Assert.assertEquals(201, response.getStatus());
        LyricResponse lyricResponse = objectMapper.readValue(response.getContentAsString(), LyricResponse.class);
        Assert.assertEquals(content, lyricResponse.getData().getContent());
        Assert.assertEquals(title, lyricResponse.getData().getTitle());
        Assert.assertFalse(Strings.isNullOrEmpty(lyricResponse.getData().getObjectId()));
        Assert.assertEquals(background, lyricResponse.getData().getCreativeBackground());
        Assert.assertEquals(location, lyricResponse.getData().getLocation());
        Assert.assertEquals(authorId, lyricResponse.getData().getAuthorId());
        Assert.assertNotNull(lyricResponse.getData().getCreatedTime());
        Assert.assertNotNull(lyricResponse.getData().getLastModifiedTime());
    }

    @Test
    public void shouldGetLyricGivenLyricObjectId() throws Exception {
        Lyric lyric = new Lyric();
        lyric.setAuthorId("123");
        String title = "标题";
        lyric.setTitle(title);
        String location = "成都";
        lyric.setLocation(location);
        LocalDateTime now = LocalDateTime.now();
        lyric.setCreatedTime(now);
        lyric.setLastModifiedTime(now);
        String content = "内容";
        lyric.setContent(content);
        String background = "背景";
        lyric.setCreativeBackground(background);
        Lyric savedLyric = repository.save(lyric);

        String url = String.format("/lyric/%s", savedLyric.getObjectId());
        MockHttpServletResponse response = performGetRequest(url);

        Assert.assertEquals(200, response.getStatus());
        LyricResponse lyricResponse = objectMapper.readValue(response.getContentAsString(), LyricResponse.class);
        Assert.assertEquals(content, lyricResponse.getData().getContent());
        Assert.assertEquals(savedLyric.getObjectId(), lyricResponse.getData().getObjectId());
        Assert.assertEquals(title, lyricResponse.getData().getTitle());
        Assert.assertEquals(location, lyricResponse.getData().getLocation());
        Assert.assertEquals("123", lyricResponse.getData().getAuthorId());
        Assert.assertEquals(background, lyricResponse.getData().getCreativeBackground());
        Assert.assertNotNull(lyricResponse.getData().getCreatedTime());
        Assert.assertNotNull(lyricResponse.getData().getLastModifiedTime());
    }
}