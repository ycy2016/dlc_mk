package com.mas.dtc.num;

/**
 * 
 * status字段枚举
 * 
 * @author Mathsys
 *
 */
public enum StatusEnum {
	/**
	 * 删除信息
	 */
	DELETE("1","是"),
	/**
	 * 未删除信息
	 */
	UNDELETE("0","否");
	
	/**
	 * 状态码
	 */
	private String statusCode;
	
	/**
	 * 状态信息
	 */
	private String statusDes;

	/**
	 * 构造器
	 * @param statusCode 状态代码
	 * @param statusDes 状态描述
	 */
	private StatusEnum(String statusCode, String statusDes) {
		this.statusCode = statusCode;
		this.statusDes = statusDes;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusDes() {
		return statusDes;
	}
	
	
}
