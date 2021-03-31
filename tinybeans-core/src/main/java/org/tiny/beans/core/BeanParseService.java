package org.tiny.beans.core;

import org.tiny.beans.core.model.BeanDefinition;
import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.annotation.Scope;
import org.tiny.beans.sdk.func.BeanPost;
import org.tiny.beans.sdk.model.ScopeType;

import java.lang.reflect.InvocationTargetException;

/**
 * @author shichaoyang
 * @Description: bean解析annotation并处理
 * @date 2021-03-29 14:47
 */
public class BeanParseService {

    public BeanParseService(){

    }

    protected  void parse(){
        //类解析并放到池子中
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Bean.class)) {

                //如果实现了beanpost接口
                if(BeanPost.class.isAssignableFrom(clazz)){
                    try {
                        BeanPost o = (BeanPost) clazz.getDeclaredConstructor().newInstance();
                        beanPosts.add(o);
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

                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setBeanClass(clazz);

                Bean beanAnnotation = (Bean) clazz.getAnnotation(Bean.class);
                String beanName = beanAnnotation.value();

                //1. 解析原型模式/单例模式
                //原型
                if (clazz.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnnotation = (Scope) clazz.getAnnotation(Scope.class);
                    beanDefinition.setScopeType(scopeAnnotation.value());
                }
                //单例
                else {
                    beanDefinition.setScopeType(ScopeType.Singleton);
                }
                beanDefinitionMap.put(beanName, beanDefinition);

                //2. 解析懒加载模式
                //TODO
            }
        }

        //2. 实例化bean
        for(String beanName: beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            //创建单例bean
            if (beanDefinition.getScopeType() == ScopeType.Singleton) {
                if (!singletonObjectPool.containsKey(beanName)) {
                    Object bean = createBean(beanName, beanDefinition);
                    singletonObjectPool.put(beanName, bean);
                }
            }
        }
    }
}
