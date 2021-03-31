package org.tiny.beans.core.model;

import org.tiny.beans.sdk.model.ScopeType;

/**
 * @author shichaoyang
 * @Description: bean上下文
 */
public class BeanDefinition {

    //类对象
    private Class beanClass;

    //原型or单例
    private ScopeType scopeType;

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(ScopeType scopeType) {
        this.scopeType = scopeType;
    }
}
