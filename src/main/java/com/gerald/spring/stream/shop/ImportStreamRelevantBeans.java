package com.gerald.spring.stream.shop;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ImportStreamRelevantBeans implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDef = new GenericBeanDefinition();
        beanDef.setBeanClass(ProducerListenerImpl.class);
        
        registry.registerBeanDefinition("producerListener", beanDef);
    }

}
