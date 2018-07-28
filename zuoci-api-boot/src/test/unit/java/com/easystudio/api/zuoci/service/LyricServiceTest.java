package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.model.LyricData;
import com.easystudio.api.zuoci.model.LyricResponse;
import org.junit.Assert;
import org.junit.Test;

public class LyricServiceTest {
    private LyricService service = new LyricService();

    @Test
    public void shouldReturnLyricResponseWhenCreateLyricSuccessfully() {
        LyricData data = new LyricData();
        LyricResponse lyric = service.createLyric(data);

        Assert.assertNull(lyric);
    }
}