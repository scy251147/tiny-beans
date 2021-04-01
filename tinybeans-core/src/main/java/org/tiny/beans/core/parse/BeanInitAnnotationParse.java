package org.tiny.beans.core.parse;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.sdk.func.BeanInit;
import java.lang.reflect.InvocationTargetException;

/**
 * @author shichaoyang
 * @Description:
 * @date 2021-04-01 12:21
 */
@Slf4j
public class BeanInitAnnotationParse extends AbstractBeanParse {

    /**
     * 处理BeanInit接口代理
     *
     * @param clazz
     */
    @Override
    public void parse(Class clazz, BeanContext beanContext) {
        if (BeanInit.class.isAssignableFrom(clazz)) {
            try {
                BeanInit o = (BeanInit) clazz.getDeclaredConstructor().newInstance();
                beanContext.getBeanInitAnnotationPool().add(o);
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
