package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 其他未知错误
 */
public class TbCreateException extends Exception{

    public TbCreateException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
