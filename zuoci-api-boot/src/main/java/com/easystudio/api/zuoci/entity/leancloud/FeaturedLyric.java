package com.easystudio.api.zuoci.entity.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("FeaturedLyric")
public class FeaturedLyric extends AVObject {
    public int getIndex() {
        return getInt("index");
    }

    public void setIndex(int value) {
        put("index", value);
    }

    public void setLyric(Lyric value) {
        put("lyric", value);
    }

    public Lyric getLyric() {
        return getAVObject("lyric");
    }
}
