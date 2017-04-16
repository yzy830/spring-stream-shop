package com.gerald.spring.stream.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.cloud.stream.binder.EmbeddedHeadersMessageConverter;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.kafka.support.ProducerListenerAdapter;
import org.springframework.messaging.support.GenericMessage;

public class ProducerListenerImpl extends ProducerListenerAdapter<byte[], byte[]> {    
    @Override
    public void onError(String topic, Integer partition, byte[] key, byte[] value, Exception exception) {
        FailedMessage failMessage = new FailedMessage(topic, partition, key, value, exception);
        System.out.println(failMessage);
    }
    
    public static class ExceptionDetail implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -5575840014470585573L;

        private String exceptionType;
        
        private String message;
        
        private String stackTrace;
        
        public ExceptionDetail(Exception exp) {
            if(exp != null) {
                exceptionType = exp.getClass().getName();
                message = exp.getMessage();
                StringWriter writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                exp.printStackTrace(printWriter);
                stackTrace = writer.toString();
            }
        }

        public String getExceptionType() {
            return exceptionType;
        }

        public String getMessage() {
            return message;
        }

        public String getStackTrace() {
            return stackTrace;
        }
    }
    
    public static class FailedMessage implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 871440122428955543L;

        private static final StringDeserializer deserializer = new StringDeserializer(); 
        
        private static final EmbeddedHeadersMessageConverter embeddedHeadersMessageConverter = new EmbeddedHeadersMessageConverter();
        
        private String topic;
        
        private Integer partition;
        
        private byte[] key;
        
        private byte[] value;
        
        private ExceptionDetail reason;
        
        private transient MessageValues resolved;
        
        FailedMessage(String topic, Integer partition, 
                      byte[] key, byte[] value,
                      Exception exception) {
            this.topic = topic;
            this.partition = partition;
            this.key = key;
            this.value = value;
            this.reason = new ExceptionDetail(exception);
            
            GenericMessage<byte[]> messegae = new GenericMessage<byte[]>(value);
            try {
                resolved = embeddedHeadersMessageConverter.extractHeaders(messegae, true);
                String result = deserializer.deserialize(topic, (byte[])resolved.getPayload());
                resolved.setPayload(result);
            } catch (Exception e) {
                // 如果不能解析，忽略异常
                resolved = new MessageValues("", Collections.<String, Object>emptyMap());
            }
        }

        public String getTopic() {
            return topic;
        }

        public Integer getPartition() {
            return partition;
        }

        public byte[] getKey() {
            return key;
        }

        public byte[] getValue() {
            return value;
        }

        public ExceptionDetail getReason() {
            return reason;
        }

        public MessageValues getResolved() {
            return resolved;
        }
        
        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            out.writeObject(resolved.getHeaders());
            out.writeObject(resolved.getPayload());
        }
        
        @SuppressWarnings("unchecked")
        private void readObject(java.io.ObjectInputStream in) throws ClassNotFoundException, IOException {
            in.defaultReadObject();
            Map<String, Object> headers = (Map<String, Object>)in.readObject();
            Object payload = in.readObject();
            
            resolved = new MessageValues(payload, headers);
        }
        
        @Override
        public String toString() {
            return debugStr(topic, partition, reason, resolved);
        }
        
        private static String debugStr(String topic, Integer partition, 
                ExceptionDetail reason, MessageValues resolved) {
            StringBuilder builder = new StringBuilder();
            
            builder.append("topic = ").append(topic).append(", ")
                   .append("partition = ").append(partition).append(", ")
                   .append("headers = ").append(resolved.getHeaders()).append(", ")
                   .append("content = ").append(resolved.getPayload()).append(", ")
                   .append("exception type = ").append(reason.getExceptionType()).append(", ")
                   .append("exception message = ").append(reason.getMessage());
            
            return builder.toString();
        }
    }
}
