package org.tiny.beans.core;

public class BeanDefinition {

    //类对象
    private Class beanClass;

    //单例
    private String scope;

    //懒加载
    private Boolean lazy;


    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }
}
