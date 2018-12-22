package com.mas.dtc.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 过滤器
 * 
 * @author Mathsys
 *
 */
public class EncodeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	

	@Override
	public void destroy() {
		System.out.println("encode ... destroy");
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			/**
			 * 对应接口如果返回字符串乱码,将响应实体设置为"utf-8"
			 */
		HttpServletResponse  httpResponse = (HttpServletResponse) response;
//			System.out.println("encode ...");
//			httpResponse.setHeader("Content-type","charset=utf-8");
//			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("charset=utf-8");
			chain.doFilter(request, httpResponse);
			
		
	}

}
