package org.tiny.beans.core.parse;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.core.model.BeanDefinition;
import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.annotation.Scope;
import org.tiny.beans.sdk.model.ScopeType;

/**
 * @author shichaoyang
 * @Description: scope注解解析
 * @date 2021-04-01 12:15
 */
@Slf4j
public class BeanScopeAnnotationParse extends AbstractBeanParse {

    /**
     * 处理@Scope注解, 解析原型模式/单例模式
     */
    @Override
    public void parse(Class clazz, BeanContext beanContext) {
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
}
