package com.mas.dtc.framework.shiromanager;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.service.ILoginService;
import com.mas.dtc.utils.IpUtil;
import com.mas.dtc.vo.ResponseResult;

/**
 * 
 * shiro的认证失败过滤器,定义验证不通过的逻辑
 * 
 * @author Mathsys
 *
 */
public class AuthenticatioinFilter extends PassThruAuthenticationFilter {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(AuthenticatioinFilter.class);

	/**
	 * 获取用户信息业务层
	 */
	@Resource(name="loginServiceImpl")
	private ILoginService loginServiceImpl;
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		LOG.info("AuthenticatioinFilter.... " );
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletresponse = (HttpServletResponse) response;
		String ip = IpUtil.getIpAddr(httpServletRequest);
		if (isLoginRequest(request, response)) {
			LOG.info("AuthenticatioinFilter.... " + ip + " isLoginRequest(request, response) ");
			/* 要考虑管理员把正在登录的用户帐号删除了,该登录帐号变为无效登录状态
			Subject subject = SecurityUtils.getSubject();
			UserInfo userInfoToken = (UserInfo) subject.getPrincipal();
			String account = userInfoToken.getAccount();
			// 用户帐号不存在
			UserInfo userInfo = loginServiceImpl.getUserFindbyAccount(account);
			if(userInfo==null){
				LOG.info("AuthenticatioinFilter.... " + ip + " account is deleted ");
				if(subject.isAuthenticated()) {
					//清楚本地subject
						subject.logout(); 
				}
				returnResponse(httpServletRequest,httpServletresponse);
				return false;
			}
			*/
			return true;
		} else {// 登录失效
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
	private void returnResponse(	HttpServletRequest httpServletRequest,HttpServletResponse httpServletresponse){
		String ip = IpUtil.getIpAddr(httpServletRequest);
		//如果是ajax请求,可以获取String字符串
		String requestType = httpServletRequest.getHeader("X-Requested-With");
		if (requestType == null) {
			/**/
			try {
				LOG.info(ip + " authen failure! redirt login.jsp ");
				final String nextPagePath = httpServletRequest.getContextPath() ;
				//   登录页面 <%=request.getContextPath()%>/dlc/user/login
				httpServletresponse.sendRedirect(nextPagePath+"/dlc/user/login");
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}

			// httpServletRequest.getRequestDispatcher("/dlc/user/login1").forward(httpServletRequest,
			// response);

		} else {
			ResponseResult reslut;
			ResponseEnum resEnum;
			LOG.info(ip + " authen failure! return json ");
			// 非ajax请求,未登录或登录失效时,重定向登录页面
			// 好处是ajax请求时,只返回json数据
			resEnum = ResponseEnum.AUTHEN_FAILURE;
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
