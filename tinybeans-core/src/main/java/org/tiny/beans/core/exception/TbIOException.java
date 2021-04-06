package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 其他未知错误
 */
public class TbIOException extends Exception{

    public TbIOException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
