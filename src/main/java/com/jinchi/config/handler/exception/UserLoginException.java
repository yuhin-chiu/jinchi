package com.jinchi.config.handler.exception;

/**
 * @author yuxuanjiao
 * @date 2017年9月30日 下午2:40:12 
 * @version 1.0
 */

public class UserLoginException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final int code;
    
    public UserLoginException() {
        super();
        code = -1;
    }
    
    public UserLoginException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
