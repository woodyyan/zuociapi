package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.configuration.DbFixture;
import com.easystudio.api.zuoci.model.JwtAuthenticationRequest;
import com.easystudio.api.zuoci.model.JwtAuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(MockMvcAutoConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:/application-test.properties")
public class AbstractControllerIntegrationTest {
    protected static final String JSON_API = "application/vnd.api+json";
    protected static final String AUTHORIZATION_HEADER = "Authorization";

    private String authorizationValue = "Bearer";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        dbFixture.addUser();

        JwtAuthenticationRequest request = new JwtAuthenticationRequest("key", "secret");
        String json = objectMapper.writeValueAsString(request);
        ResultActions result = mockMvc.perform(post("/auth").accept(JSON_API).content(json));
        MockHttpServletResponse response = result.andReturn().getResponse();
        JwtAuthenticationResponse jwtAuthenticationResponse = objectMapper.readValue(response.getContentAsString(), JwtAuthenticationResponse.class);
        authorizationValue = jwtAuthenticationResponse.getToken();
    }

    protected ResultActions performRequest(String url) throws Exception {
        return mockMvc.perform(get(url).accept(JSON_API).header(AUTHORIZATION_HEADER, authorizationValue));
    }
}
