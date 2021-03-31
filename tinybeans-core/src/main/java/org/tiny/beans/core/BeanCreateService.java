package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.core.model.BeanDefinition;
import org.tiny.beans.sdk.annotation.Inject;
import org.tiny.beans.sdk.func.BeanInit;
import org.tiny.beans.sdk.func.BeanPost;
import org.tiny.beans.sdk.model.ScopeType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author shichaoyang
 * @Description: bean处理器
 * @date 2021-03-29 11:26
 */
public class BeanCreateService {

    /**
     * 带参构造
     * @param beanContext
     */
    public BeanCreateService(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    //上下文对象
    private BeanContext beanContext;

    /**
     * 创建单例bean实例，原型bean实例无需创建，因为每次都会new新的出来
     */
    protected void createSingletonBean() {
        for (String beanName : beanContext.getBeanDefinitionPool().keySet()) {
            BeanDefinition beanDefinition = beanContext.getBeanDefinitionPool().get(beanName);
            //创建单例bean
            if (beanDefinition.getScopeType() == ScopeType.Singleton) {
                if (!beanContext.getBeanSingletonPool().containsKey(beanName)) {
                    Object bean = createBean(beanName, beanDefinition);
                    beanContext.getBeanSingletonPool().put(beanName, bean);
                }
            }
        }
    }

    /**
     * 创建bean实例
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
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
            for (BeanPost beanPost : beanContext.getBeanPostAnnotationPool()) {
                object = beanPost.postProcessBeforeInitialization(beanPost, beanName);
            }

            //4. 用户自定义初始化
            if (object instanceof BeanInit) {
                ((BeanInit) (object)).afterPropertiesSet();
            }

            //5. 初始化后处理
            for (BeanPost beanPost : beanContext.getBeanPostAnnotationPool()) {
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

    /**
     * 获取bean实例
     * @param beanName
     * @return
     */
    protected Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanContext.getBeanDefinitionPool().get(beanName);
        //原型模式，每次都需要创建bean
        if (beanDefinition.getScopeType() == ScopeType.Prototype) {
            Object bean = createBean(beanName, beanDefinition);
            return bean;
        }
        //单例模式，直接返回原有bean
        else if (beanDefinition.getScopeType() == ScopeType.Singleton) {
            Object object = beanContext.getBeanSingletonPool().get(beanName);
            if (object == null) {
                object = createBean(beanName, beanDefinition);
                beanContext.getBeanSingletonPool().put(beanName, beanDefinition);
            }
            return object;
        }
        return null;
    }

}
