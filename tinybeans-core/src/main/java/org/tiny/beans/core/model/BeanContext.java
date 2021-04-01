package org.tiny.beans.core.model;

import lombok.Data;
import org.tiny.beans.sdk.func.BeanInit;
import org.tiny.beans.sdk.func.BeanPost;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shichaoyang
 * @Description: bean上下文
 */
@Data
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
    private Map<String, BeanPost> beanPostAnnotationPool = new ConcurrentHashMap<>();

    //bean带有BeanInit注解池
    private Map<String, BeanInit> beanInitAnnotationPool = new ConcurrentHashMap<>();

    //bean中Value注解池
    private Map<String, String> beanValuePool = new ConcurrentHashMap<>();

}
