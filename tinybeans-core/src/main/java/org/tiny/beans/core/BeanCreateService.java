package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanDefinition;
import org.tiny.beans.sdk.annotation.Inject;
import org.tiny.beans.sdk.func.BeanInit;
import org.tiny.beans.sdk.func.BeanPost;
import org.tiny.beans.sdk.model.ScopeType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shichaoyang
 * @Description: bean处理器
 * @date 2021-03-29 11:26
 */
public class BeanCreateService {

    public BeanCreateService(Map<String, BeanDefinition> beanDefinitionMap, Map<String, Object> singletonObjectPool, List<BeanPost> beanPosts) {
        this.beanDefinitionMap = beanDefinitionMap;
        this.singletonObjectPool = singletonObjectPool;
        this.beanPosts = beanPosts;
    }

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private Map<String, Object> singletonObjectPool = new ConcurrentHashMap<>();

    private List<BeanPost> beanPosts = new ArrayList<>();


    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        try {
            //1. 类实例化
            Class beanClass = beanDefinition.getBeanClass();
            Object object = beanClass.getDeclaredConstructor().newInstance();

            //2. 属性填充, 将userService中的user对象填充进去
            Field[] declaredFields = beanClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(Inject.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(object, bean);
                }
            }

            //3. 初始化前处理
            for (BeanPost beanPost : beanPosts) {
                object = beanPost.postProcessBeforeInitialization(beanPost, beanName);
            }

            //4. 用户自定义初始化
            if (object instanceof BeanInit) {
                ((BeanInit) (object)).afterPropertiesSet();
            }

            //5. 初始化后处理
            for (BeanPost beanPost : beanPosts) {
                object = beanPost.postProcessAfterInitialization(beanPost, beanName);
            }
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        //原型模式，每次都需要创建bean
        if (beanDefinition.getScopeType() == ScopeType.Prototype) {
            Object bean = createBean(beanName, beanDefinition);
            return bean;
        }
        //单例模式，直接返回原有bean
        else if (beanDefinition.getScopeType() == ScopeType.Singleton) {
            Object object = singletonObjectPool.get(beanName);
            if (object == null) {
                object = createBean(beanName, beanDefinition);
                singletonObjectPool.put(beanName, beanDefinition);
            }
            return object;
        }
        return null;
    }

}
