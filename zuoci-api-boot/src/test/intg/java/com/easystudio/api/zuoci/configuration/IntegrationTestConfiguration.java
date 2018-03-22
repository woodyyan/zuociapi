package com.easystudio.api.zuoci.configuration;

import com.easystudio.api.zuoci.repository.StubUserRepository;
import com.easystudio.api.zuoci.repository.UserRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class IntegrationTestConfiguration {
    @Bean
    public DbFixture dbFixture() {
        return new DbFixture();
    }
}
