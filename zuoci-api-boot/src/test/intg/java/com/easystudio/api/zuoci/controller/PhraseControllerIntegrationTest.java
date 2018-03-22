package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.Phrases;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;

public class PhraseControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    public void shouldGetPhrases() throws Exception {

        ResultActions result = performRequest("/phrase");
        MockHttpServletResponse response = result.andReturn().getResponse();

        Phrases phrases = objectMapper.readValue(response.getContentAsString(), Phrases.class);
        Assert.assertThat(response.getStatus(), is(HttpStatus.UNAUTHORIZED.value()));
        Assert.assertThat(phrases.getMeta().getPageNumber(), is(0));
    }
}
