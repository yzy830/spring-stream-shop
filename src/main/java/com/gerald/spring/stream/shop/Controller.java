package com.gerald.spring.stream.shop;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Resource(name = "createShop")
    private MessageChannel createShopChannel;
    
    @Autowired
    private BinderAwareChannelResolver resolver;
    
    private int count = 0;
    
    @RequestMapping("/create")
    public String createShop() {
        Shop shop = new Shop();
        shop.setAddress("123");
        shop.setClosed(false);
        shop.setName("shop-name-" + count++);
        
        Message<Shop> message = MessageBuilder.withPayload(shop).build();
        createShopChannel.send(message);
        
        return "done";
    }
    
    @RequestMapping("/close")
    public String closeShop() {
        Shop shop = new Shop();
        shop.setAddress("123");
        shop.setClosed(true);
        shop.setName("close-shop-name-" + count++);    
        
        Message<Shop> message = MessageBuilder.withPayload(shop).build();

        resolver.resolveDestination("closeShop").send(message);
        
        return "done";
    }
}
