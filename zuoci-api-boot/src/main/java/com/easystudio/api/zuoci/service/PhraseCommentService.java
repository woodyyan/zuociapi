package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.repository.PhraseCommentRepository;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildNotFoundError;

@Service
public class PhraseCommentService {

    @Autowired
    private PhraseCommentRepository repository;

    @Autowired
    private PhraseCommentTranslator translator;

    public void createComment(CommentData data) {
        PhraseComment phraseComment = translator.toPhraseComment(data);
        repository.save(phraseComment);
    }

    public Page<PhraseComment> searchComment(Long phraseId, boolean isVisible, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        return repository.findByPhraseIdAndIsVisible(phraseId, isVisible, pageable);
    }

    public void deleteComment(Long phraseId, Long objectId) {
        PhraseComment comment = repository.findOne(objectId);
        if (comment != null) {
            if (comment.getPhraseId().equals(phraseId)) {
                repository.delete(objectId);
            } else {
                Error error = buildNotFoundError("Phrase id is not the same with comment");
                throw new ErrorException(HttpStatus.BAD_REQUEST, error);
            }
        } else {
            Error error = buildNotFoundError("Phrase comment not found");
            throw new ErrorException(HttpStatus.NOT_FOUND, error);
        }
    }
}
