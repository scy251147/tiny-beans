package org.tiny.beans.core.model;

import org.tiny.beans.sdk.func.BeanPost;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shichaoyang
 * @Description: bean上下文
 */
public class BeanContext {

    //bean配置扫描类
    private Class configClass;

    //类文件池
    private List<Class> classPool = new ArrayList<>();

    //bean定义池
    private Map<String, BeanDefinition> beanDefinitionPool = new ConcurrentHashMap<>();

    //bean单例池
    private Map<String, Object> beanSingletonPool = new ConcurrentHashMap<>();

    //bean带有BeanPost注解池
    private List<BeanPost> beanPostAnnotationPool = new ArrayList<>();

    //bean中Value注解池
    private Map<String, String> beanValuePool = new ConcurrentHashMap<>();

    public Map<String, BeanDefinition> getBeanDefinitionPool() {
        return beanDefinitionPool;
    }

    public void setBeanDefinitionPool(Map<String, BeanDefinition> beanDefinitionPool) {
        this.beanDefinitionPool = beanDefinitionPool;
    }

    public Map<String, Object> getBeanSingletonPool() {
        return beanSingletonPool;
    }

    public void setBeanSingletonPool(Map<String, Object> beanSingletonPool) {
        this.beanSingletonPool = beanSingletonPool;
    }

    public List<BeanPost> getBeanPostAnnotationPool() {
        return beanPostAnnotationPool;
    }

    public void setBeanPostAnnotationPool(List<BeanPost> beanPostAnnotationPool) {
        this.beanPostAnnotationPool = beanPostAnnotationPool;
    }

    public Class getConfigClass() {
        return configClass;
    }

    public void setConfigClass(Class configClass) {
        this.configClass = configClass;
    }

    public List<Class> getClassPool() {
        return classPool;
    }

    public void setClassPool(List<Class> classPool) {
        this.classPool = classPool;
    }

    public Map<String, String> getBeanValuePool() {
        return beanValuePool;
    }

    public void setBeanValuePool(Map<String, String> beanValuePool) {
        this.beanValuePool = beanValuePool;
    }
}
