package org.tiny.beans.sdk.annotation;

import java.lang.annotation.*;

/**
 * 字段赋值
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Value {
    String value();
}
