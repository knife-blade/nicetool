package com.suchtool.betterutil.configuration;

import com.suchtool.betterutil.util.ApplicationContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BetterUtilConfiguration {
    @Bean(name = "suchtool.betterutil.applicationContextHolder")
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}
