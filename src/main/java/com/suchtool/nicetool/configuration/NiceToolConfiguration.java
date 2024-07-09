package com.suchtool.nicetool.configuration;

import com.suchtool.nicetool.property.NiceToolProperty;
import com.suchtool.nicetool.util.spring.ApplicationContextHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "com.suchtool.niceTool.niceToolConfiguration", proxyBeanMethods = false)
public class NiceToolConfiguration {
    @Bean(name = "com.suchtool.nicetool.applicationContextHolder")
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Bean(name = "com.suchtool.nicetool.niceToolProperty")
    @ConfigurationProperties(prefix = "suchtool.nicetool")
    public NiceToolProperty niceToolProperty() {
        return new NiceToolProperty();
    }
}
