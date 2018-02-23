package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.PagingMeta;
import com.easystudio.api.zuoci.model.PhraseComments;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<PhraseComments> toPhraseCommentResponse(Page<PhraseComment> pagedComments) {
        PhraseComments body = new PhraseComments();

        List<CommentData> data = new ArrayList<>();
        for (PhraseComment comment : pagedComments.getContent()) {
            CommentData commentData = toCommentData(comment);
            data.add(commentData);
        }
        body.setData(data);
        PagingMeta meta = new PagingMeta();
        meta.setPageNumber(pagedComments.getNumber());
        meta.setPageSize(pagedComments.getSize());
        meta.setTotalElements(pagedComments.getTotalElements());
        meta.setTotalPages(pagedComments.getTotalPages());
        body.setMeta(meta);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public CommentData toCommentData(PhraseComment comment) {
        CommentData data = new CommentData();
        data.setRepliedUserId(comment.getRepliedUserId());
        data.setPhraseId(comment.getPhraseId());
        data.setPhraseAuthorId(comment.getPhraseAuthorId());
        data.setParentId(comment.getParentId());
        data.setParentCommentId(comment.getParentCommentId());
        data.setObjectId(comment.getObjectId());
        data.setLastModifiedTime(comment.getLastModifiedTime().toDateTime());
        data.setCreatedTime(comment.getCreatedTime().toDateTime());
        data.setContent(comment.getContent());
        data.setCommentatorId(comment.getCommentatorId());
        return data;
    }
}
