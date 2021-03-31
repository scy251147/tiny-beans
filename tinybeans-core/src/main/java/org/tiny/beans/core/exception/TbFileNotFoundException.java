package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 找不到文件
 */
public class TbFileNotFoundException extends Exception{

    public TbFileNotFoundException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
