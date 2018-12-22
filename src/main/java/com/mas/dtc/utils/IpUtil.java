package com.mas.dtc.utils;


import javax.servlet.http.HttpServletRequest;

/**
 * 获取真实ip工具类
 * @author Mathsys
 *
 */
public class IpUtil {
		
	/** 
     * 获取当前网络ip 
     * @param request 请求实例
     * @return  ip地址
     */  
    public static String getIpAddr(HttpServletRequest request){  
    	 String ip = request.getHeader("x-forwarded-for");    
    	    if(ip ==  null|| ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
    	        ip = request.getHeader("Proxy-Client-IP");    
    	    }    
    	    if(ip ==  null|| ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
    	        ip = request.getHeader("WL-Proxy-Client-IP");    
    	    }    
    	    if(ip ==  null|| ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
    	        ip = request.getRemoteAddr();    
    	    }    
    	    return ip;  
    }  
	
}
