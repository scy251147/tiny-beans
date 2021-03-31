package org.tiny.beans.core.model;

import org.tiny.beans.sdk.model.ScopeType;

public class BeanDefinition {

    //类对象
    private Class beanClass;

    //原型or单例
    private ScopeType scopeType;

    //懒加载
    private Boolean lazy;


    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(ScopeType scopeType) {
        this.scopeType = scopeType;
    }
}