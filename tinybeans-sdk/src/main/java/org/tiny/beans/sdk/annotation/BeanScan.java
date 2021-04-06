package org.tiny.beans.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bean扫描
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeanScan {

    /**
     * 包扫描路径
     * @return
     */
    String packagePath() default "";

    /**
     * 包配置文件
     * @return
     */
    String packageConfig() default "";

}
