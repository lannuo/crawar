package com.cskj.crawar.exception;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;

	public AppException() {
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}
}
