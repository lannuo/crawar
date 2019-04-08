package com.cskj.crawar.exception;

public class AppException extends RuntimeException {
	private Integer code;
	private String msg;
	private Throwable cause;

	public AppException(Integer code, String msg, Throwable cause) {
		this.code = code;
		this.msg = msg;
		this.cause = cause;
	}

	public AppException(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public AppException(Integer code, Throwable cause) {
		this.code = code;
		this.cause = cause;
	}

	public AppException(String msg, Throwable cause) {
		this.msg = msg;
		this.cause = cause;
	}

	public AppException(Throwable cause) {
		this.cause = cause;
	}

	public AppException(Integer code) {
		this.code = code;
	}

	public AppException(String msg) {
		this.msg = msg;
	}

	public AppException() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
}
