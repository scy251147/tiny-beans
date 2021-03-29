package org.tiny.beans.sdk.model;

/**
 * 类创建的两种模式
 * 原型模式：每次请求都创建新类
 * 单例模式：一次创建，处处使用
 */
public enum ScopeType {

    Prototype("prototype",1),
    Singleton("singleton",2);

    ScopeType(String desc, int code){
        this.desc = desc;
        this.code = code;
    }

    private String desc;

    private  int code;

}
