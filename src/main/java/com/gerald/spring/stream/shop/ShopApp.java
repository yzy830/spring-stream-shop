package com.gerald.spring.stream.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(ShopChannels.class)
@EnableSpringStream
public class ShopApp {       
    public static void main( String[] args ) {
        SpringApplication.run(ShopApp.class, args);
    }
}
