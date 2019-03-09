package com.app.util.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootWebApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       return builder.sources(SpringBootWebApplication.class);
    }
}
