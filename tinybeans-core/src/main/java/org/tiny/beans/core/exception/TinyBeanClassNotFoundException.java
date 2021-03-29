package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 找不到类文件
 * @date 2021-03-29 10:45
 */
public class TinyBeanClassNotFoundException extends Exception{

    public TinyBeanClassNotFoundException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
