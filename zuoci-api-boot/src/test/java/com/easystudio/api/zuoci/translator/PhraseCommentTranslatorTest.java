package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class PhraseCommentTranslatorTest {

    private PhraseCommentTranslator translator = new PhraseCommentTranslator();

    @Test
    public void shouldTranslateToPhraseCommentGivenPhraseCommentData() {
        CommentData data = new CommentData();
        data.setCommentatorId("1");
        data.setContent("content");
        data.setCreatedTime(DateTime.now());
        data.setLastModifiedTime(DateTime.now());
        data.setObjectId(123L);
        data.setParentCommentId(234L);
        data.setParentId(345L);
        data.setPhraseAuthorId("2");
        data.setPhraseId(456L);
        data.setRepliedUserId("3");

        PhraseComment comment = translator.toPhraseComment(data);

        Assert.assertThat(comment.getCommentatorId(), is("1"));
        Assert.assertThat(comment.getContent(), is("content"));
        Assert.assertThat(comment.getCreatedTime(), lessThanOrEqualTo(LocalDateTime.now()));
        Assert.assertThat(comment.getLastModifiedTime(), lessThanOrEqualTo(LocalDateTime.now()));
        Assert.assertThat(comment.getParentCommentId(), is(234L));
        Assert.assertThat(comment.getParentId(), is(345L));
        Assert.assertThat(comment.getPhraseAuthorId(), is("2"));
        Assert.assertThat(comment.getPhraseId(), is(456L));
        Assert.assertThat(comment.getRepliedUserId(), is("3"));
    }
}