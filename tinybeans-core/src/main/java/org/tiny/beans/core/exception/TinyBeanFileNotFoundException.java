package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 找不到文件
 * @date 2021-03-29 10:47
 */
public class TinyBeanFileNotFoundException  extends Exception{

    public TinyBeanFileNotFoundException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
