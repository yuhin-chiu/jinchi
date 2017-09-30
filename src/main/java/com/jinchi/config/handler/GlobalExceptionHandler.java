package com.jinchi.config.handler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinchi.config.handler.exception.UserLoginException;
import com.jinchi.enums.ApiResponseEnum;
import com.jinchi.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = null;

    @ResponseBody
    @ExceptionHandler(UserLoginException.class)
    public ApiResponse handleUserLoginException(ServletRequest req, ServletResponse res, UserLoginException ex) {
        ApiResponse response = new ApiResponse();
        response.setCode(ApiResponseEnum.NON_LOGIN_IN.getCode());
        response.setMsg(ApiResponseEnum.NON_LOGIN_IN.getContent());
        return response;
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse handleMissingParam(ServletRequest req, ServletResponse res, Exception ex) {
        ApiResponse response = new ApiResponse();
        response.setMsg(ex.getMessage());
        response.setCode(HttpStatus.BAD_REQUEST.value());
        return response;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(ServletRequest req, ServletResponse res, Exception ex) {
        logger = LoggerFactory.getLogger(ex.getStackTrace()[0].getClassName());
        logger.error(ex.getStackTrace()[0].getLineNumber() + " | "
                + ex.getClass().getName() + " | "
                + ex.getMessage(),
                ex);
        ApiResponse response = new ApiResponse();
        response.setMsg(ApiResponseEnum.INTERNAL_ERROR.getContent());
        response.setCode(ApiResponseEnum.INTERNAL_ERROR.getCode());
        return response;
    }
}
