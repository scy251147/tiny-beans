package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 其他未知错误
 */
public class TinyBeanException extends Exception{

    public TinyBeanException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
