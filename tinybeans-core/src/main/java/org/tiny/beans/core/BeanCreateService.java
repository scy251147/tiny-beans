package org.tiny.beans.core;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.exception.TbException;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.core.model.BeanDefinition;
import org.tiny.beans.sdk.annotation.Inject;
import org.tiny.beans.sdk.annotation.Value;
import org.tiny.beans.sdk.func.BeanInit;
import org.tiny.beans.sdk.func.BeanPost;
import org.tiny.beans.sdk.model.ScopeType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author shichaoyang
 * @Description: bean处理器
 */
@Slf4j
public class BeanCreateService {

    /**
     * 带参构造
     *
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
    protected void createSingletonBean() throws TbException {
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
     * 获取bean实例
     *
     * @param beanName
     * @return
     */
    protected Object getBean(String beanName) throws TbException {
        BeanDefinition beanDefinition = beanContext.getBeanDefinitionPool().get(beanName);
        if (beanDefinition == null) {
            return null;
        }
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

    /**
     * 创建bean实例
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) throws TbException {
        //1. 类实例化
        Class beanClass = beanDefinition.getBeanClass();
        Object beanObject = newBeanInstance(beanClass);

        //2. inject注入填充, 比如将userService中的user对象填充进去
        injectBeanSet(beanClass, beanObject);

        //3. value注入填充
        injectValueSet(beanClass, beanObject);

        //4. 初始化前处理
        BeanPost beanPostBefore = beanContext.getBeanPostAnnotationPool().get(beanName);
        if (beanPostBefore != null) {
            beanObject = beanPostBefore.postProcessBeforeInitialization(beanObject, beanName);
        }

        //5. 用户自定义初始化
        BeanInit beanInit = beanContext.getBeanInitAnnotationPool().get(beanName);
        if (beanInit != null) {
            beanInit.afterPropertiesSet();
        }

        //6. 初始化后处理
        BeanPost beanPostAfter = beanContext.getBeanPostAnnotationPool().get(beanName);
        if (beanPostAfter != null) {
            beanObject = beanPostAfter.postProcessAfterInitialization(beanObject, beanName);
        }

        return beanObject;
    }

    /**
     * 将class创建出实例出来
     * @param beanClass
     * @return
     */
    private Object newBeanInstance(Class beanClass) {
        try {
            Object object = beanClass.getDeclaredConstructor().newInstance();
            return object;
        } catch (InstantiationException e) {
            log.error("new bean instance error", e);
        } catch (IllegalAccessException e) {
            log.error("new bean instance error", e);
        } catch (InvocationTargetException e) {
            log.error("new bean instance error", e);
        } catch (NoSuchMethodException e) {
            log.error("new bean instance error", e);
        }
        return null;
    }

    /**
     * 将beanClass中的inject字段的属性赋值
     * @param beanClass
     * @param object
     */
    private void injectBeanSet(Class beanClass, Object object) throws TbException {
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Inject.class)) {
                Object bean = getBean(declaredField.getName());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(object, bean);
                } catch (IllegalAccessException e) {
                    throw new TbException("inject bean error, bean class :" + beanClass, e);
                }
            }
        }
    }

    /**
     * 将beanClass中的value字段的属性赋值
     * @param beanClass
     * @param object
     */
    private void injectValueSet(Class beanClass, Object object) throws TbException {
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Value.class)) {
                //获取@Value注解的key
                String filedKey = declaredField.getAnnotation(Value.class).value();
                //获取@Value注解的值
                String fieldVal = beanContext.getConfigPool().get(filedKey);
                if (fieldVal == null) {
                    throw new TbException("can't find config node:" + filedKey, null);
                }
                //类型转换
                declaredField.setAccessible(true);
                valueSetByType(declaredField, object, filedKey, fieldVal);
            }
        }
    }

    /**
     * 根据数据类型进行值设置
     * @param declaredField
     * @param object
     * @param filedKey
     * @param fieldVal
     * @throws TbException
     */
    private void valueSetByType(Field declaredField, Object object, String filedKey, String fieldVal) throws TbException {
        try {
            if (declaredField.getType().getName().equals("int")) {
                declaredField.set(object, Integer.parseInt(fieldVal));
            } else if (declaredField.getType().getName().equals("string")) {
                declaredField.set(object, fieldVal);
            } else if (declaredField.getType().getName().equals("boolean")) {
                declaredField.set(object, Boolean.parseBoolean(fieldVal));
            } else if (declaredField.getType().getName().equals("long")) {
                declaredField.set(object, Long.parseLong(fieldVal));
            } else {
                declaredField.set(object, fieldVal);
            }
        } catch (IllegalAccessException e) {
            throw new TbException("inject config value error, config node:" + filedKey, e);
        }
    }
}
