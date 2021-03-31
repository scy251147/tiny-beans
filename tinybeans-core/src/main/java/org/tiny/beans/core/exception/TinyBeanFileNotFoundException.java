package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 找不到文件
 */
public class TinyBeanFileNotFoundException  extends Exception{

    public TinyBeanFileNotFoundException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
