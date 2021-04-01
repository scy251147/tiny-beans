package org.tiny.beans.core.model;

import lombok.Data;
import org.tiny.beans.sdk.model.ScopeType;

/**
 * @author shichaoyang
 * @Description: bean上下文
 */
@Data
public class BeanDefinition {

    //类对象
    private Class beanClass;

    //原型or单例
    private ScopeType scopeType;

}
