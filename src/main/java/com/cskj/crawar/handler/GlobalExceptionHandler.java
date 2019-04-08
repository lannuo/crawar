package com.cskj.crawar.handler;

import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.exception.AppException;
import com.github.pagehelper.PageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OperInfo handleMethodArgumentException(MethodArgumentNotValidException ex) {
        return new OperInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OperInfo handleAppException(AppException ex) {
        return new OperInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMsg());
    }

    @ExceptionHandler(PageException.class)
    @ResponseStatus(HttpStatus.OK)
    public OperInfo handlePageException(PageException ex) {
        return new OperInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public OperInfo handleException(Exception ex) {
        return new OperInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}