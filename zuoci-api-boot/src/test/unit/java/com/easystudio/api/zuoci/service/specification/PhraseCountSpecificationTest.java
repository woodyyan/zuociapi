package com.easystudio.api.zuoci.service.specification;

import com.easystudio.api.zuoci.entity.Phrase;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Collections;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(EasyMockRunner.class)
public class PhraseCountSpecificationTest extends EasyMockSupport {

    @TestSubject
    private PhraseCountSpecification specification = new PhraseCountSpecification("", "", true);

    @Mock
    private Root<Phrase> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private Path<Object> path;

    @Mock
    private Predicate predicate;

    @Mock
    private Path<Object> createdTimePath;

    @Test
    public void shouldGetPredicateGivenContentAndAuthorIdAndVisible() {
        setField(specification, "content", "content");
        setField(specification, "authorId", "123");
        setField(specification, "isVisible", true);

        expect(root.get("authorId")).andReturn(path);
        expect(cb.equal(path, "123")).andReturn(predicate);
        expect(root.get("isVisible")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(cb.and(Arrays.asList(predicate, predicate).toArray(new Predicate[2]))).andReturn(predicate);

        replayAll();
        Predicate predicate = specification.toPredicate(root, query, cb);
        verifyAll();

        Assert.assertNotNull(predicate);
    }

    @Test
    public void shouldGetPredicateGivenContentAndAuthorIdAndVisibleIsNull() {
        setField(specification, "content", "content");
        setField(specification, "authorId", "123");
        setField(specification, "isVisible", null);

        expect(root.get("authorId")).andReturn(path);
        expect(cb.equal(path, "123")).andReturn(predicate);
        expect(cb.and(Collections.singletonList(predicate).toArray(new Predicate[1]))).andReturn(predicate);

        replayAll();
        Predicate predicate = specification.toPredicate(root, query, cb);
        verifyAll();

        Assert.assertNotNull(predicate);
    }

    @Test
    public void shouldGetPredicateGivenContentAndAuthorIdIsEmptyAndVisible() {
        setField(specification, "content", "content");
        setField(specification, "authorId", "");
        setField(specification, "isVisible", true);

        expect(root.get("createdTime")).andReturn(createdTimePath);
        expect(cb.greaterThan(anyObject(), anyObject(LocalDateTime.class))).andReturn(predicate);
        expect(root.get("content")).andReturn(path);
        expect(cb.equal(path, "content")).andReturn(predicate);
        expect(root.get("isVisible")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(cb.and(Arrays.asList(predicate, predicate, predicate).toArray(new Predicate[2]))).andReturn(predicate);

        replayAll();
        Predicate predicate = specification.toPredicate(root, query, cb);
        verifyAll();

        Assert.assertNotNull(predicate);
    }
}