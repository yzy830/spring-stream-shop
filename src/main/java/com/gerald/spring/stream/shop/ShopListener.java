package com.gerald.spring.stream.shop;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class ShopListener {
    @StreamListener("createShopResp")
    public void onShopCreationResponse(String response) {
        System.out.println("received response <" + response + ">");
    }
}
