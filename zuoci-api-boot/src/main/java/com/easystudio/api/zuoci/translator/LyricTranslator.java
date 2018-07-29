package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Lyric;
import com.easystudio.api.zuoci.model.LyricData;
import com.easystudio.api.zuoci.model.LyricResponse;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class LyricTranslator {
    public Lyric toLyricEntity(LyricData data) {
        Lyric lyric = new Lyric();
        lyric.setAuthorId(data.getAuthorId());
        lyric.setTitle(data.getTitle());
        lyric.setLocation(data.getLocation());
        LocalDateTime now = LocalDateTime.now();
        lyric.setLastModifiedTime(now);
        lyric.setCreatedTime(now);
        lyric.setCreativeBackground(data.getCreativeBackground());
        lyric.setContent(data.getContent());
        return lyric;
    }

    public LyricResponse toLyricResponse(Lyric lyric) {
        LyricResponse lyricResponse = new LyricResponse();
        lyricResponse.getData().setLastModifiedTime(lyric.getLastModifiedTime().toDateTime());
        lyricResponse.getData().setCreatedTime(lyric.getCreatedTime().toDateTime());
        lyricResponse.getData().setObjectId(lyric.getObjectId());
        lyricResponse.getData().setTitle(lyric.getTitle());
        lyricResponse.getData().setContent(lyric.getContent());
        lyricResponse.getData().setLocation(lyric.getLocation());
        lyricResponse.getData().setCreativeBackground(lyric.getCreativeBackground());
        lyricResponse.getData().setAuthorId(lyric.getAuthorId());
        lyricResponse.getData().setDeleted(lyric.isDeleted());
        lyricResponse.getData().setValid(lyric.isValid());
        lyricResponse.getData().setVisible(lyric.isVisible());

        return lyricResponse;
    }
}
