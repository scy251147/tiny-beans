package org.tiny.beans.core.parse;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.sdk.annotation.Bean;
import org.tiny.beans.sdk.func.BeanPost;
import java.lang.reflect.InvocationTargetException;

/**
 * @author shichaoyang
 * @Description: post注解解析
 * @date 2021-04-01 12:14
 */
@Slf4j
public class BeanPostAnnotationParse extends AbstractBeanParse {

    /**
     * 处理BeanPost接口代理
     *
     * @param clazz
     */
    @Override
    public void parse(Class clazz, BeanContext beanContext) {
        //如果实现了beanpost接口
        if (BeanPost.class.isAssignableFrom(clazz)) {
            try {
                BeanPost o = (BeanPost) clazz.getDeclaredConstructor().newInstance();

                Bean beanAnnotation = (Bean) clazz.getAnnotation(Bean.class);
                String beanName = beanAnnotation.value();

                beanContext.getBeanPostAnnotationPool().put(beanName, o);
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
}
