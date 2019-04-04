package com.cskj.crawar.handler;

import com.cskj.crawar.entity.common.OperInfo;
import com.cskj.crawar.entity.common.RespCode;
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
        OperInfo operInfo=new OperInfo();
        operInfo.setOk(false);
        operInfo.setMsg(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        operInfo.setCode(RespCode.FAIL);
        return operInfo;
    }

	@ExceptionHandler(AppException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public OperInfo handleAppException(HttpServletRequest request, Exception ex) {
		OperInfo oper = new OperInfo();
		oper.setOk(false);
		oper.setMsg(ex.getMessage());
		oper.setCode(RespCode.FAIL);
		return oper;
	}

	@ExceptionHandler(PageException.class)
	@ResponseStatus(HttpStatus.OK)
	public OperInfo handlePageException(HttpServletRequest request, Exception ex) {
        OperInfo oper = new OperInfo();
        oper.setOk(false);
        oper.setMsg(ex.getMessage());
        oper.setCode(RespCode.FAIL);
        return oper;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public OperInfo handleException(HttpServletRequest request, Exception ex) {
        OperInfo oper = new OperInfo();
        oper.setMsg(ex.getMessage());
        oper.setCode(RespCode.FAIL);
        return oper;
	}

}