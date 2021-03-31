package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 其他未知错误
 */
public class TbException extends Exception{

    public TbException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
