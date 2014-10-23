package com.torychow.bat.exception;

public class CodeInfoException extends RuntimeException {

	private static final long serialVersionUID = 601468657162379138L;

	private int code = -1;

	private String codeInfo;

	public CodeInfoException(int code) {
		this.code = code;
	}

	public CodeInfoException(String codeInfo) {
		super(codeInfo);
		this.codeInfo = codeInfo;
	}

	public CodeInfoException(int code, String codeInfo) {
		super(codeInfo);
		this.code = code;
		this.codeInfo = codeInfo;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}

}
