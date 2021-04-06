package org.tiny.beans.core.create;

import org.tiny.beans.core.BeanQueryService;
import org.tiny.beans.core.exception.TbException;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.sdk.annotation.Inject;
import java.lang.reflect.Field;

/**
 * @author shichaoyang
 * @Description: bean创建期间，对inject注解处理
 * @date 2021-04-06 19:29
 */
public class BeanInjectAnnotationOnCreate extends AbstractBeanCreate{

    //bean查询服务
    private static BeanQueryService beanQueryService;

    /**
     * 将beanClass中的inject字段的属性赋值
     * @param beanClass
     * @param object
     */
    @Override
    public void beanSet(Class beanClass, Object object, BeanContext beanContext) throws TbException {
        beanQueryService = new BeanQueryService(beanContext);
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Inject.class)) {
                Object bean = beanQueryService.getBean(declaredField.getName());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(object, bean);
                } catch (IllegalAccessException e) {
                    throw new TbException("inject bean error, bean class :" + beanClass, e);
                }
            }
        }
    }
}
