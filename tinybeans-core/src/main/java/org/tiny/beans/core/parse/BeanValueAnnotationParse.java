package org.tiny.beans.core.parse;

import org.tiny.beans.core.model.BeanContext;

/**
 * @author shichaoyang
 * @Description: value注解解析
 * @date 2021-04-01 12:15
 */
public class BeanValueAnnotationParse implements BeanParse{

    /**
     * 处理@Value注解
     *
     * 将value对应的字段key+值解析后放到map中，以便于创建bean的时候直接赋值
     *
     * @param clazz
     */
    @Override
    public void parse(Class clazz, BeanContext beanContext) {
        //todo 解析类，拿到@Value值，并将值结果放到beanValuePool中备用
    }
}