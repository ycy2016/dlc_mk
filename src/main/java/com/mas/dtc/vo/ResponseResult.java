package com.mas.dtc.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回封装类
 * 
 * @author Mathsys
 *
 */
public class ResponseResult {

	/**
	 * 结果
	 */
	Map<String,Object> header = new HashMap<>();
	
	/**
	 * 接口状态
	 */
	Integer code;
	
	/**
	 * 接口信息
	 */
	String msg;
	
	
	/**
	 * 构造方法
	 * @param code  接口状态
	 * @param msg 接口信息
	 */
	public ResponseResult(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 构造方法
	 * @param code 接口状态
	 * @param msg 接口信息
	 * @param header 信息
	 */
	public ResponseResult(Integer code,String msg,Map<String, Object> header){
		this.code = code;
		this.msg = msg;
		this.header = header;
	}
	
	public Map<String, Object> getHeader() {
		return header;
	}
	public void setHeader(Map<String, Object> header) {
		this.header = header;
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
	public String toString() {
		return "ResponseResult [header=" + header + ", code=" + code + ", msg=" + msg + "]";
	}
	
}
