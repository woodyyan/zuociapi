package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(MockMvcAutoConfiguration.class)
@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:/application-test.properties")
public class AbstractControllerIntegrationTest {
    protected static final String JSON_API = "application/vnd.api+json";
    protected static final String AUTHORIZATION_HEADER = "Authorization";

    private String authorizationValue = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjo0NDI0NzY5OTM3LCJpYXQiOjE1MjE3Mjk5Mzd9.tb013UptHLas3JT3Ks0AL1E4jC7IV9EIqrAVye8LkVNdxrM_DoMm3c_AHeb33kkQVKpbgeKBvALsB8ny3_cEDQ";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setUp() {
    }

    MockHttpServletResponse performGetRequest(String url) throws Exception {
        return mockMvc.perform(get(url).accept(JSON_API).header(AUTHORIZATION_HEADER, authorizationValue))
                .andReturn()
                .getResponse();
    }

    MockHttpServletResponse performPostRequest(String url, PhraseLikeRequest body) throws Exception {
        String bodyContent = objectMapper.writeValueAsString(body);
        return mockMvc.perform(post(url)
                .header(AUTHORIZATION_HEADER, authorizationValue)
                .contentType(JSON_API)
                .content(bodyContent))
                .andReturn()
                .getResponse();
    }
}
