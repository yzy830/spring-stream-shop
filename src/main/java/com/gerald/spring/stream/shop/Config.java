package com.gerald.spring.stream.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.ProducerListener;

@Configuration
public class Config {
    @SuppressWarnings("rawtypes")
    @Bean
    public ProducerListener producerListener() {
        return new ProducerListenerImpl();
    }
}
