package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.LyricRequest;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

public class LyricControllerIntegrationTest extends AbstractControllerIntegrationTest {

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
}