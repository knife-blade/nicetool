package com.suchtool.nicetool.configuration;

import com.suchtool.nicetool.util.spring.ApplicationContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "com.suchtool.niceTool.niceToolConfiguration", proxyBeanMethods = false)
public class NiceToolConfiguration {
    @Bean(name = "suchtool.nicetool.applicationContextHolder")
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}
