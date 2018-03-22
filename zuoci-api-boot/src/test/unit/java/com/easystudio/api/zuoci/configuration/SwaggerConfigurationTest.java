package com.easystudio.api.zuoci.configuration;

import com.fasterxml.classmate.TypeResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.spring.web.plugins.Docket;

import static org.springframework.test.util.ReflectionTestUtils.setField;

public class SwaggerConfigurationTest {
    private SwaggerConfiguration configuration = new SwaggerConfiguration();

    @Before
    public void setUp() throws Exception {
        setField(configuration, "version", "1.0");
        setField(configuration, "name", "name");
        setField(configuration, "description", "description");
        setField(configuration, "email", "email");
        setField(configuration, "includePatterns", "includePatterns");
        setField(configuration, "protocol", "protocol");
    }

    @Test
    public void shouldCreateTypeResolverBean() {
        TypeResolver typeResolver = configuration.typeResolver();
        Assert.assertNotNull(typeResolver);
    }

    @Test
    public void shouldCreateDocketBean() {
        Docket docket = configuration.createDocket();
        Assert.assertNotNull(docket);
        Assert.assertEquals(docket.getGroupName(), "Zuoci v1");
    }
}