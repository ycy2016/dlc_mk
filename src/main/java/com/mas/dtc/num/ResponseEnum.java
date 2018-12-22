package com.mas.dtc.num;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 错误情况枚举
 * 
 * @author Mathsys
 *
 */
public enum ResponseEnum {
	/**
	 * 1000,"登录成功"
	 */
	LOGIN_SUCCESS(1000,"登录成功"),
	/**
	 * 1001,"帐号不存在"
	 */
	LOGIN_NO_ACCOUNT(1001,"帐号不存在"),
	/**
	 *1002,"帐号/密码错误"
	 */
	LOGIN_FAILURE(1002,"帐号/密码错误"),
	/**
	 * 1003,"退出成功"
	 */
	LOGOUT_SUCCESS(1003,"退出成功"),
	/**
	 * 2000,"用户添加成功"
	 */
	ADD_SUCCESS(2000,"用户添加成功"),
	/**
	 * 2001,"用户编辑成功"
	 */
	UPDATE_SUCCESS(2001,"用户编辑成功"),
	/**
	 * 2003,"用户已经存在"
	 */
	ACCOUNT_EXISTS(2003,"用户已经存在"),
	/**
	 *  2003,"帐号已经存在"
	 */
	USE_EXISTS(2003,"帐号已经存在"),
	
	/**
	 * 2004,"操作失败"
	 */
	ACTION_FAILURE(2004,"操作失败"),
	
	/**
	 * 3000	查询成功
	 */
	QUERY_SUCCESS(3000,"查询成功"),
	/**
	 * 3001	查无记录
	 */
	QUERY_FAILURE(3001,"查无记录"),

	/**
	 * 8000	解析成功
	 */
	PARSE_SUCCESS(8000,"解析成功"),
	/**
	 * 8001	解析错误
	 */
	PARSE_FAILURE(8001,"解析错误"),
	/**
	 * 8002	系统错误
	 */
	SYSTEM_FAILURE(8002,"系统错误"),
	/**
	 * 8003	登录失效,请重新登录
	 */
	AUTHEN_FAILURE(8003,"登录失效,请重新登录"),
	/**
	 * 8003	登录失效,请重新登录
	 */
	AUTHOR_FAILURE(8004,"无限权,请联系管理员");
	
	/**
	 * 状态码
	 */
	private Integer code;
	/**
	 * 信息
	 */
	private String msg;
	
	/**
	 * 构造方法
	 * @param code 状态码
	 * @param msg 信息
	 */
	ResponseEnum(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 获取实例状态码和信息
	 * @return  {"code":1000,"msg":"登录成功"}
	 */
	public Map<String,Object> getResponse(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code",this.code );
		map.put("msg", this.msg);
		
		return map;
	}
	
	public Integer getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}
}
