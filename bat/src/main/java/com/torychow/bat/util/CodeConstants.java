package com.torychow.bat.util;

/**
 * 
 * @author Leo
 * @Description 状态码
 * @ClassName CodeConstants
 * @Date create at 2013-11-18 下午5:14:22
 * @Commpany 游戏蜗牛
 */
public class CodeConstants {

	public static final int DEFAULT_ERROR_CODE = -1;
	public static final String DEFAULT_ERROR_CODEINFO = "操作失败";

	public static final int DEFAULT_SUCCSESS_CODE = 0;
	public static final String DEFAULT_SUCCSESS_CODEINFO = "操作成功";

//	public static final int NO_AUTHORITY_CODE = -100;
//	public static final String NO_AUTHORITY_CODEINFO = "您没有权限查看行为监控";
//
//	public static final int ACCOUNT_NOT_FOUND_ERROR = -601;
//	public static final String ACCOUNT_NOT_FOUND_ERROR_INFO = "对不起,您的账号目前状态异常!";
	
	public static final int LOGIN_ERROR_CODE=-10000;
	public static final String LOGIN_ERROR_CODEINFO="身份验证失败";
	
	public static final int SECOND_LOGIN_ERROR_CODE=-20000;
	public static final String SECOND_LOGIN_ERROR_CODEINFO="此操作需要进行二次身份验证";
}
