package com.easystudio.api.zuoci.service.specification;

import com.easystudio.api.zuoci.entity.Phrase;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

public class PhraseCountSpecification implements Specification<Phrase> {
    private String authorId;
    private String content;
    private Boolean isVisible;

    public PhraseCountSpecification(String content, String authorId, Boolean isVisible) {
        this.authorId = authorId;
        this.content = content;
        this.isVisible = isVisible;
    }

    @Override
    public Predicate toPredicate(Root<Phrase> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (isNullOrEmpty(authorId)) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime yesterday = new LocalDateTime(
                    now.getYear(),
                    now.getMonthOfYear(),
                    now.getDayOfMonth(),
                    0, 0, 0);
            Path<LocalDateTime> createdTimePath = root.get("createdTime");
            Predicate createdTimePredicate = cb.greaterThan(createdTimePath, yesterday);
            predicates.add(createdTimePredicate);

            Path<String> contentPath = root.get("content");
            Predicate contentPredicate = cb.equal(contentPath, content);
            predicates.add(contentPredicate);

            Path<Boolean> visiblePath = root.get("isVisible");
            Predicate visiblePredicate = cb.equal(visiblePath, isVisible);
            predicates.add(visiblePredicate);
        } else {
            Path<String> authorIdPath = root.get("authorId");
            Predicate authorIdPredicate = cb.equal(authorIdPath, authorId);
            predicates.add(authorIdPredicate);

            if (isVisible != null) {
                Path<Boolean> visiblePath = root.get("isVisible");
                Predicate visiblePredicate = cb.equal(visiblePath, isVisible);
                predicates.add(visiblePredicate);
            }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
