package com.app.util.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.app.util.message.validator.domain.MessagePattern;
import com.app.util.message.validator.domain.StandAlone;

@Configuration
class SpringDataRestConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.exposeIdsFor(StandAlone.class);
                config.exposeIdsFor(MessagePattern.class);
            }
        };
    }
}