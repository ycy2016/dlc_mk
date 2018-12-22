package com.mas.dtc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Component;

import com.mas.dtc.framework.aop.annotation.LogAnnoation;
import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.ILoginService;
import com.mas.dtc.utils.IpUtil;
import com.mas.dtc.vo.ResponseResult;

/**
 * 登录controller
 * 
 * @author Mathsys
 *
 */
@Path("/dlc/user")
@Component
public class LoginController {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(LoginController.class);
	
	/**
	 * 获取用户信息业务层
	 */
	@Resource(name = "loginServiceImpl")
	private ILoginService loginServiceImpl;
	
	/**
	 * 跳转登录页面
	 * @return Viewable 登录页面
	 */
	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	public Viewable forwardLogin() {
		//web中 url配置成/dlc/* 就可以成功访问
		return new Viewable("/WEB-INF/login/login");
	}

	/*	
	@GET
	@Path("/login2")
	@Produces("application/json")
	public Map<String,String>  forwardLogin2(@Context HttpServletRequest httpServletRequest,@Context ServletResponse response) {
		Map<String,String> map = new HashMap<String,String>(){
			{
				put("like","案犯岁的");
			}
		};
		
		return map;
	
	}
	*/	
	

	
	/**
	 * 跳转主页面
	 * @return  Viewable 登录后的主页
	 */
	@GET
	@Path("/home")
	@Produces(MediaType.TEXT_HTML)
	public Viewable forwardHome() {
		// "/WEB-INF/jsp/homePage" 不可以简写成homePage,jersey会自己不会找找
		return new Viewable("/WEB-INF/basicData/basicData");
	}

	/*
	 * 跳转到主页
	 * @return Viewable
	 
	@GET
	@Path("/home2")
	@Produces(MediaType.TEXT_HTML)
	public Viewable forwardHome2() {
		// "/WEB-INF/jsp/homePage" 不可以简写成homePage,jersey会自己不会找找
		return new Viewable("/WEB-INF/jsp/homePage");
	}
	 */
	/**
	 * 登入
	 * @param request 请求
	 * @param account 帐号
	 * @param password 密码
	 * @return 以json格式响应结果,LOGIN_FAILURE 或者 LOGIN_SUCCESS
	 */
	@LogAnnoation(operation="登录")
	@POST
	@Path("/dologin")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult doLogin(@Context HttpServletRequest request, @FormParam("account") String account,
			@FormParam("password") String password) {
		String ip = IpUtil.getIpAddr(request);
//		LOG.info(ip + " 登录校验  " + account +";"+ password);
		ResponseResult reslut;
		ResponseEnum resEnum;
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(account);
		token.setPassword(password.toCharArray());
		token.setHost(ip);
		// 获取当前Subject 
		Subject subject = SecurityUtils.getSubject();
		try {
			// 调用安全认证框架的登录方法,回调MyRealm中的AuthenticationInfo方法
			subject.login(token);
			resEnum = ResponseEnum.LOGIN_SUCCESS;
		}catch (IncorrectCredentialsException ic) {
			LOG.info(ic.getMessage());
			//帐号or密码失败
			resEnum = ResponseEnum.LOGIN_FAILURE;
		} catch (UnknownAccountException ex) {
//			LOG.info(ex.getMessage());
			//帐号不存在
			resEnum = ResponseEnum.LOGIN_NO_ACCOUNT;
		}
		reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg());
		return reslut;
	}

	/**
	 * 返回登录用户的用户信息(包括用户名、角色)
	 * @return 以json格式响应结果,QUERY_SUCCESS或者QUERY_FAILURE
	 */
	@GET
	@Path("/getuserinfo")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult queryUserInfo() {
		ResponseResult reslut;
		ResponseEnum resEnum;
		// 获取当前Subject 
		Subject subject = SecurityUtils.getSubject();
		UserInfo tokenUserInfo = (UserInfo) subject.getPrincipal();
		//去数据库中取数据的原因是要获取用户最新数据
		UserInfo userInfo = loginServiceImpl.getUserFindbyAccount(tokenUserInfo.getAccount());
		if (userInfo!=null) {
			//密码置空
			userInfo.setPassword(null);
			resEnum = ResponseEnum.QUERY_SUCCESS;
		}else{
			resEnum = ResponseEnum.QUERY_FAILURE;
		}
		Map<String,Object> header = new HashMap<>();
	
		header.put("data", userInfo);
		reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg(),header);
		return reslut;
	}
	
	/**
	 * 退出
	 * @return LOGOUT_SUCCESS或者ACTION_FAILURE
	 */
	@GET
	@Path("/logout")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult outLogin() {
		ResponseResult reslut;
		ResponseEnum resEnum;
		// 获取当前Subject 
		Subject subject = SecurityUtils.getSubject();
		UserInfo userInfo = (UserInfo) subject.getPrincipal();
		String account = userInfo.getAccount();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			LOG.info("\"" + account +"\""+ " logout success");
			resEnum = ResponseEnum.LOGOUT_SUCCESS;
		}else{
			resEnum = ResponseEnum.ACTION_FAILURE;
		}

		reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg());
		return reslut;
	}

}
