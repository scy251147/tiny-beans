package org.tiny.beans.core.create;

import org.tiny.beans.core.exception.TbException;
import org.tiny.beans.core.model.BeanContext;

/**
 * @author shichaoyang
 * @Description: bean对象创建时期需要进行的逻辑处理
 * @date 2021-04-06 19:27
 */
public  abstract class AbstractBeanCreate {

   public abstract void beanSet(Class beanClass, Object object, BeanContext beanContext) throws TbException;

}
