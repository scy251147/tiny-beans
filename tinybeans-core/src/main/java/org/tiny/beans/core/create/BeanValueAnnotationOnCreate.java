package org.tiny.beans.core.create;

import org.tiny.beans.core.exception.TbException;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.sdk.annotation.Value;

import java.lang.reflect.Field;

/**
 * @author shichaoyang
 * @Description: bean创建期间，对value注解处理
 * @date 2021-04-06 19:30
 */
public class BeanValueAnnotationOnCreate extends AbstractBeanCreate{

    /**
     * 将beanClass中的value字段的属性赋值
     * @param beanClass
     * @param object
     */
    @Override
    public void beanSet(Class beanClass, Object object, BeanContext beanContext) throws TbException {
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
