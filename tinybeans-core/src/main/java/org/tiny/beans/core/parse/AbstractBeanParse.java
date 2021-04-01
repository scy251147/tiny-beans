package org.tiny.beans.core.parse;

import org.tiny.beans.core.model.BeanContext;

/**
 * @author shichaoyang
 * @Description: bean解析虚类
 * @date 2021-04-01 15:30
 */
public abstract class AbstractBeanParse {

    /**
     * 业务解析
     * @param clazz
     * @param beanContext
     */
    public abstract void parse(Class clazz, BeanContext beanContext);

}
