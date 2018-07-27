package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.PhraseComments;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.repository.PhraseCommentRepository;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildNotFoundError;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class PhraseCommentService {

    @Autowired
    private PhraseCommentRepository repository;

    @Autowired
    private PhraseCommentTranslator translator;

    public CommentData createComment(CommentData data) {
        PhraseComment phraseComment = translator.toPhraseComment(data);
        PhraseComment comment = repository.save(phraseComment);
        return translator.toCommentData(comment);
    }

    public PhraseComments searchComment(Long phraseId, String userId, boolean isVisible, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        if (isNullOrEmpty(userId)) {
            Page<PhraseComment> pagedComments = repository.findByPhraseIdAndIsVisible(phraseId, isVisible, pageable);
            return translator.toPhraseComments(pagedComments);
        } else {
            Page<PhraseComment> phraseComments = repository.findAll(getCommentSpec(userId, isVisible), pageable);
            return translator.toPhraseComments(phraseComments);
        }
    }

    private Specification<PhraseComment> getCommentSpec(String userId, boolean isVisible) {
        return (root, query, cb) -> {
            Path<Object> repliedUserId = root.get("repliedUserId");
            Path<Object> authorId = root.get("phraseAuthorId");
            Path<Boolean> visible = root.get("isVisible");
            Predicate repliedUserPredicate = cb.equal(repliedUserId, userId);
            Predicate authorPredicate = cb.equal(authorId, userId);
            Predicate visiblePredicate = cb.equal(visible, isVisible);
            return cb.and(cb.or(repliedUserPredicate, authorPredicate), visiblePredicate);
        };
    }

    public void deleteComment(Long objectId) {
        PhraseComment comment = repository.findOne(objectId);
        if (comment != null) {
            repository.delete(objectId);
        } else {
            Error error = buildNotFoundError("Phrase comment not found");
            throw new ErrorException(HttpStatus.NOT_FOUND, error);
        }
    }

    public CommentData getComment(Long objectId) {
        PhraseComment comment = repository.findOne(objectId);
        return translator.toCommentData(comment);
    }

    public Long countComment(Long phraseId, String userId, boolean isVisible) {
        if (isNullOrEmpty(userId)) {
            return repository.countByPhraseId(phraseId, isVisible);
        } else {
            return repository.countByUserId(userId, isVisible);
        }
    }
}
