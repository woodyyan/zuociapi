package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Lyric;
import com.easystudio.api.zuoci.model.LyricData;
import com.easystudio.api.zuoci.model.LyricResponse;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

public class LyricTranslatorTest {
    private LyricTranslator translator = new LyricTranslator();

    @Test
    public void shouldReturnLyricEntityGivenLyricDataWhenTranslate() {
        String authorId = "123";
        String background = "背景";
        String location = "成都";
        String content = "内容";
        String title = "标题";
        LyricData data = new LyricData();
        data.setAuthorId(authorId);
        data.setCreativeBackground(background);
        data.setLocation(location);
        data.setContent(content);
        data.setTitle(title);
        data.setObjectId("123");

        Lyric lyric = translator.toLyricEntity(data);

        Assert.assertEquals(authorId, lyric.getAuthorId());
        Assert.assertEquals(content, lyric.getContent());
        Assert.assertEquals(background, lyric.getCreativeBackground());
        Assert.assertEquals(location, lyric.getLocation());
        Assert.assertEquals(title, lyric.getTitle());
        Assert.assertTrue(lyric.isValid());
        Assert.assertTrue(lyric.isVisible());
        Assert.assertFalse(lyric.isDeleted());
        Assert.assertNotNull(lyric.getCreatedTime());
        Assert.assertNotNull(lyric.getLastModifiedTime());
    }

    @Test
    public void shouldReturnLyricResponseGivenLyricWhenTranslate() {
        String authorId = "123";
        String background = "背景";
        String location = "成都";
        String content = "内容";
        String title = "标题";

        Lyric lyric = new Lyric();
        lyric.setContent(content);
        lyric.setCreativeBackground(background);
        LocalDateTime now = LocalDateTime.now();
        lyric.setCreatedTime(now);
        lyric.setLastModifiedTime(now);
        lyric.setLocation(location);
        lyric.setTitle(title);
        lyric.setObjectId("abc");
        lyric.setAuthorId(authorId);

        LyricResponse lyricResponse = translator.toLyricResponse(lyric);

        Assert.assertEquals(content, lyricResponse.getData().getContent());
        Assert.assertEquals(authorId, lyricResponse.getData().getAuthorId());
        Assert.assertEquals(location, lyricResponse.getData().getLocation());
        Assert.assertEquals(background, lyricResponse.getData().getCreativeBackground());
        Assert.assertEquals(title, lyricResponse.getData().getTitle());
        Assert.assertEquals("abc", lyricResponse.getData().getObjectId());
        Assert.assertNotNull(lyricResponse.getData().getCreatedTime());
        Assert.assertNotNull(lyricResponse.getData().getLastModifiedTime());
    }
}