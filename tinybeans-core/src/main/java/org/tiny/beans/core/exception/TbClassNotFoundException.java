package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description: 找不到类文件
 */
public class TbClassNotFoundException extends Exception{

    public TbClassNotFoundException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
