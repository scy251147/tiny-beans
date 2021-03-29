package org.tiny.beans.sdk.func;

/**
 * bean初始化前和初始化后执行的一些业务逻辑
 */
public interface BeanPost {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);

}
