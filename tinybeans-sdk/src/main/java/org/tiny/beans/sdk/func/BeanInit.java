package org.tiny.beans.sdk.func;

/**
 * bean初始化需要执行的一些业务逻辑
 */
public interface BeanInit {

    void afterPropertiesSet();

}
