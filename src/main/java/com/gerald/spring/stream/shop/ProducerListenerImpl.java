package com.gerald.spring.stream.shop;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.ProducerListenerAdapter;

public class ProducerListenerImpl extends ProducerListenerAdapter<byte[], byte[]> {
    private StringDeserializer deserializer = new StringDeserializer(); 
    
    @Override
    public void onError(String topic, Integer partition, byte[] key, byte[] value, Exception exception) {
        String result = deserializer.deserialize(topic, value);
        System.out.println("result = " + result);
        System.out.println("fail to send message. target = <" + topic + ":" + partition + ">, " + "key = " + key + ", value = " + value);
        exception.printStackTrace();
    }
}
