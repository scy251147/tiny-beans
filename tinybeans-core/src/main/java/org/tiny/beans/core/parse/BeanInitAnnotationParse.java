package org.tiny.beans.core.parse;

import lombok.extern.slf4j.Slf4j;
import org.tiny.beans.core.model.BeanContext;
import org.tiny.beans.sdk.annotation.Bean;
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

                Bean beanAnnotation = (Bean) clazz.getAnnotation(Bean.class);
                String beanName = beanAnnotation.value();

                beanContext.getBeanInitAnnotationPool().put(beanName, o);
            } catch (InstantiationException e) {
                log.error("new bean instance error", e);
            } catch (IllegalAccessException e) {
                log.error("new bean instance error", e);
            } catch (InvocationTargetException e) {
                log.error("new bean instance error", e);
            } catch (NoSuchMethodException e) {
                log.error("new bean instance error", e);
            }
        }
    }
}
