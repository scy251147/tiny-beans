package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 错误
 * @date 2021-03-29 10:48
 */
public class TinyBeanException extends Exception{

    public TinyBeanException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
