package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class PhraseCommentTranslator {
    public PhraseComment toPhraseComment(CommentData data) {
        PhraseComment comment = new PhraseComment();
        comment.setContent(data.getContent());
        comment.setCommentatorId(data.getCommentatorId());
        comment.setCreatedTime(LocalDateTime.now());
        comment.setLastModifiedTime(LocalDateTime.now());
        comment.setParentCommentId(data.getParentCommentId());
        comment.setParentId(data.getParentId());
        comment.setPhraseAuthorId(data.getPhraseAuthorId());
        comment.setPhraseId(data.getPhraseId());
        comment.setRepliedUserId(data.getRepliedUserId());
        return comment;
    }
}
