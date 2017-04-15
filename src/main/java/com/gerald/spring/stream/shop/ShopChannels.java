package com.gerald.spring.stream.shop;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ShopChannels {
    @Output
    MessageChannel createShop();
    
    @Input
    SubscribableChannel createShopResp();
    
    @Output
    MessageChannel closeShop(); 
}
