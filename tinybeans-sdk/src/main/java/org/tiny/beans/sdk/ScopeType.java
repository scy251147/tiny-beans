package org.tiny.beans.sdk;

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
