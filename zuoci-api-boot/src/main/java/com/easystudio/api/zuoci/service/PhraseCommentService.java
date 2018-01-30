package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.repository.PhraseCommentRepository;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<PhraseComment> searchComment(Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        return repository.findAll(pageable);
    }
}
