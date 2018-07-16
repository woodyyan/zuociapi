package com.easystudio.api.zuoci.service.specification;

import com.easystudio.api.zuoci.entity.Phrase;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Collections;

import static org.easymock.EasyMock.expect;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(EasyMockRunner.class)
public class PhraseSpecificationTest extends EasyMockSupport {
    @TestSubject
    private PhraseSpecification specification = new PhraseSpecification(true, true, "");

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

    @Test
    public void shouldGetPredicateGivenIsValidAndIsVisibleAndAuthorId() {
        setField(specification, "isValid", true);
        setField(specification, "authorId", "123");
        setField(specification, "isVisible", true);

        expect(root.get("isValid")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(root.get("authorId")).andReturn(path);
        expect(cb.equal(path, "123")).andReturn(predicate);
        expect(root.get("isVisible")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(cb.and(Arrays.asList(predicate, predicate, predicate).toArray(new Predicate[3]))).andReturn(predicate);

        replayAll();
        Predicate predicate = specification.toPredicate(root, query, cb);
        verifyAll();

        Assert.assertNotNull(predicate);
    }

    @Test
    public void shouldGetPredicateGivenIsValidAndIsVisibleAndAuthorIdIsEmpty() {
        setField(specification, "isValid", true);
        setField(specification, "authorId", "");
        setField(specification, "isVisible", true);

        expect(root.get("isValid")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(root.get("isVisible")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(cb.and(Arrays.asList(predicate, predicate).toArray(new Predicate[2]))).andReturn(predicate);

        replayAll();
        Predicate predicate = specification.toPredicate(root, query, cb);
        verifyAll();

        Assert.assertNotNull(predicate);
    }

    @Test
    public void shouldGetPredicateGivenIsValidAndIsVisibleIsNullAndAuthorIdIsEmpty() {
        setField(specification, "isValid", true);
        setField(specification, "authorId", "");
        setField(specification, "isVisible", null);

        expect(root.get("isValid")).andReturn(path);
        expect(cb.equal(path, true)).andReturn(predicate);
        expect(cb.and(Collections.singletonList(predicate).toArray(new Predicate[1]))).andReturn(predicate);

        replayAll();
        Predicate predicate = specification.toPredicate(root, query, cb);
        verifyAll();

        Assert.assertNotNull(predicate);
    }
}