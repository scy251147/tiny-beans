package org.tiny.beans.core.exception;

/**
 * @author shichaoyang
 * @Description:
 * @date 2021-03-31 21:14
 */
public class TbQueryException extends Exception{

    public TbQueryException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
