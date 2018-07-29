package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Lyric;
import com.easystudio.api.zuoci.model.LyricRequest;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.easystudio.api.zuoci.repository.LyricRepository;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.when;

public class LyricControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @MockBean
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

        Lyric savedLyric = new Lyric();
        savedLyric.setContent(content);
        LocalDateTime now = LocalDateTime.now();
        savedLyric.setCreatedTime(now);
        savedLyric.setCreativeBackground(background);
        savedLyric.setLastModifiedTime(now);
        savedLyric.setLocation(location);
        savedLyric.setTitle(title);
        savedLyric.setObjectId("123");
        savedLyric.setAuthorId(authorId);

        when(repository.save(Mockito.any(Lyric.class))).thenReturn(savedLyric);

        MockHttpServletResponse response = performPostRequest(url, lyricRequest);

        Assert.assertEquals(201, response.getStatus());
        LyricResponse lyricResponse = objectMapper.readValue(response.getContentAsString(), LyricResponse.class);
        Assert.assertEquals(content, lyricResponse.getData().getContent());
        Assert.assertEquals(title, lyricResponse.getData().getTitle());
        Assert.assertEquals("123", lyricResponse.getData().getObjectId());
        Assert.assertEquals(background, lyricResponse.getData().getCreativeBackground());
        Assert.assertEquals(location, lyricResponse.getData().getLocation());
        Assert.assertEquals(authorId, lyricResponse.getData().getAuthorId());
        Assert.assertNotNull(lyricResponse.getData().getCreatedTime());
        Assert.assertNotNull(lyricResponse.getData().getLastModifiedTime());
    }
}