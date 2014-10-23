package com.torychow.bat.exception;

public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = 8632645692279024784L;

	public PermissionDeniedException() {
		super();
	}

	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionDeniedException(String message) {
		super(message);
	}

	public PermissionDeniedException(Throwable cause) {
		super(cause);
	}

}
