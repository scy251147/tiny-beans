package org.tiny.beans.test.service;

import org.tiny.beans.sdk.Bean;
import org.tiny.beans.sdk.BeanPost;

/**
 * 可以做初始化前和初始化后的工作，这里可以实现真正意义的动态代理
 * 即返回和原来bean完全不一样的bean对象
 */
@Bean
public class UserPostProcessService implements BeanPost {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前，对象是原来的bean"+bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Object o = new Object();
        System.out.println("初始化后，对象是新的另外的bean，动态代理过了" + o);
        return o;
    }
}
