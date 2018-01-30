package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.repository.PhraseCommentRepository;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
