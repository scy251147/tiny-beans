package org.tiny.beans.core.parse;

import org.tiny.beans.core.model.BeanContext;

/**
 * @author shichaoyang
 * @Description:
 * @date 2021-04-01 12:10
 */
public interface BeanParse {

    /**
     * 业务解析
     * @param clazz
     * @param beanContext
     */
    void parse(Class clazz, BeanContext beanContext);

}
