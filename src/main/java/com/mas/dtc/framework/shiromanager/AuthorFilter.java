package com.mas.dtc.framework.shiromanager;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import com.alibaba.fastjson.JSON;
import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.utils.IpUtil;
import com.mas.dtc.vo.ResponseResult;

/**
 * 
 * shiro的权限失败过滤器,定义验权失败后的逻辑
 * 
 * @author Mathsys
 *
 */
public class AuthorFilter extends RolesAuthorizationFilter  {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(AuthorFilter.class);
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletresponse = (HttpServletResponse) response;
		if(isAccessAllowed(request,response,mappedValue)){
			LOG.info("isAccessAllowed OK");
			return true;
		}else{
			LOG.info("isAccessAllowed failure!!!");
			returnResponse(httpServletRequest,httpServletresponse);
			return false;
		}
		
	}
	

	/**
	 * 非ajax请求,未登录或登录失效时,重定向登录页面
	 * 好处是ajax请求时,只返回json数据
	 * @param httpServletRequest 请求实例
	 * @param httpServletresponse 响应实例
	 */
	private void returnResponse(HttpServletRequest httpServletRequest,HttpServletResponse httpServletresponse){
		String ip = IpUtil.getIpAddr(httpServletRequest);
		//如果是ajax请求,可以获取String字符串
		String requestType = httpServletRequest.getHeader("X-Requested-With");
		if (requestType == null) {
			try {
				final String nextPagePath = httpServletRequest.getContextPath() ;
				//   登录页面 <%=request.getContextPath()%>/dlc/user/login
//				httpServletresponse.sendRedirect(nextPagePath+"/dlc/user/login");
				httpServletresponse.sendRedirect(nextPagePath+"/dlc/warn/unauthor");
//				 httpServletRequest.getRequestDispatcher("/dlc/warn/unauthor").forward(httpServletRequest,
//						 httpServletresponse);
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			} 
//			catch (ServletException e) {
//				LOG.error(e.getMessage(), e);
//				e.printStackTrace();
//			}
		} else {
			ResponseResult reslut;
			ResponseEnum resEnum;
			// 非ajax请求,未登录或登录失效时,重定向登录页面
			// 好处是ajax请求时,只返回json数据
			resEnum = ResponseEnum.AUTHOR_FAILURE;
			reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg());
			httpServletresponse.setContentType("application/json; charset=utf-8");
			httpServletresponse.setCharacterEncoding("UTF-8");
			String userJson = JSON.toJSONString(reslut);
			OutputStream out = null;
			try {
				out = httpServletresponse.getOutputStream();
				out.write(userJson.getBytes("UTF-8"));
				out.flush();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		
	}
	
	
}
