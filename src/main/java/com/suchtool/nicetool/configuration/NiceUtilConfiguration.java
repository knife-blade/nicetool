package com.suchtool.nicetool.configuration;

import com.suchtool.nicetool.util.spring.ApplicationContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NiceUtilConfiguration {
    @Bean(name = "suchtool.niceutil.applicationContextHolder")
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}
