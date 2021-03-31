package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.core.model.BeanDefinition;
import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.annotation.Scope;
import org.tiny.beans.sdk.func.BeanPost;
import org.tiny.beans.sdk.model.ScopeType;
import java.lang.reflect.InvocationTargetException;

/**
 * @author shichaoyang
 * @Description: bean解析annotation并处理
 */
public class BeanParseService {

    public BeanParseService(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    //上下文对象
    private BeanContext beanContext;

    /**
     * 解析类上面的注解
     */
    protected void parse() {

        //类解析并放到池子中
        for (Class clazz : beanContext.getClassPool()) {
            if (clazz.isAnnotationPresent(Bean.class)) {
                parseBeanPostAnnotation(clazz);
                parseScopeAnnotation(clazz);
                parseLazyAnnotation(clazz);
            }
        }


    }

    /**
     * 处理BeanPost注解
     * @param clazz
     */
    private void parseBeanPostAnnotation(Class clazz) {
        //如果实现了beanpost接口
        if (BeanPost.class.isAssignableFrom(clazz)) {
            try {
                BeanPost o = (BeanPost) clazz.getDeclaredConstructor().newInstance();
                beanContext.getBeanPostAnnotationPool().add(o);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理Scope注解, 解析原型模式/单例模式
     */
    private void parseScopeAnnotation(Class clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);
        //原型
        if (clazz.isAnnotationPresent(Scope.class)) {
            Scope scopeAnnotation = (Scope) clazz.getAnnotation(Scope.class);
            beanDefinition.setScopeType(scopeAnnotation.value());
        }
        //单例
        else {
            beanDefinition.setScopeType(ScopeType.Singleton);
        }

        Bean beanAnnotation = (Bean) clazz.getAnnotation(Bean.class);
        String beanName = beanAnnotation.value();

        //将bean信息添加到池子中
        beanContext.getBeanDefinitionPool().put(beanName, beanDefinition);
    }

    /**
     * 处理Lazy注解
     * @param clazz
     */
    private void parseLazyAnnotation(Class clazz){

    }
}
