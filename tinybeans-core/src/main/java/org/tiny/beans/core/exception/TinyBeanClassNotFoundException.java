package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 找不到类文件
 */
public class TinyBeanClassNotFoundException extends Exception{

    public TinyBeanClassNotFoundException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
