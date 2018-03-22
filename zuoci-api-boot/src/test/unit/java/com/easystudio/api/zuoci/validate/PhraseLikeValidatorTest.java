package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.LikeType;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import org.junit.Test;

public class PhraseLikeValidatorTest {

    private PhraseLikeValidator validator = new PhraseLikeValidator();

    @Test
    public void shouldValidateUserIdGivenValidLikeRequest() {
        PhraseLikeRequest request = new PhraseLikeRequest();
        request.setLikeType(LikeType.InspirationLike);
        request.setPhraseId(1L);
        request.setUserId("userid");
        validator.validate(1L, request);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionWhenUserIdIsMissing() {
        PhraseLikeRequest request = new PhraseLikeRequest();
        request.setLikeType(LikeType.InspirationLike);
        request.setPhraseId(1L);
        validator.validate(1L, request);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionWhenPhraseIdsAreNotMatch() {
        PhraseLikeRequest request = new PhraseLikeRequest();
        request.setLikeType(LikeType.InspirationLike);
        request.setPhraseId(1L);
        request.setUserId("userid");
        validator.validate(2L, request);
    }
}