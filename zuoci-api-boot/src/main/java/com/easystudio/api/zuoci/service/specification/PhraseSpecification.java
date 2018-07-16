package com.easystudio.api.zuoci.service.specification;

import com.easystudio.api.zuoci.entity.Phrase;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

public class PhraseSpecification implements Specification<Phrase> {
    private Boolean isValid;
    private String authorId;
    private Boolean isVisible;

    public PhraseSpecification(Boolean isValid, Boolean isVisible, String authorId) {
        this.isValid = isValid;
        this.isVisible = isVisible;
        this.authorId = authorId;
    }

    @Override
    public Predicate toPredicate(Root<Phrase> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Path<Boolean> isValidPath = root.get("isValid");
        Predicate isValidPredicate = cb.equal(isValidPath, isValid);
        predicates.add(isValidPredicate);

        if (!isNullOrEmpty(authorId)) {
            Path<Object> authorIdPath = root.get("authorId");
            Predicate authorPredicate = cb.equal(authorIdPath, authorId);
            predicates.add(authorPredicate);
        }

        if (isVisible != null) {
            Path<Boolean> visiblePath = root.get("isVisible");
            Predicate visiblePredicate = cb.equal(visiblePath, isVisible);
            predicates.add(visiblePredicate);
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
