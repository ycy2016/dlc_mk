package com.mas.dtc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;

import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.vo.ResponseResult;

/**
 * 权限控制页面
 * 
 * @author Mathsys
 *
 */
@Path("/dlc/warn")
public class WarnController {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(DataController.class);

	/**
	 * 权限失败
	 *   这个方法不要使用@GET注解, 因为用户可以当使用put请求时或者get请求时,
	 *   发生验权限错误,所以这个方法上不能加请求方式注解
	 * @return Viewable
	 */
	@GET
	@Path("/unauthor")
	@Produces({MediaType.TEXT_HTML,MediaType.APPLICATION_JSON})
	 public Viewable forwardUnauthor() {
//	public ResponseResult forwardUnauthor() {
		LOG.info("author failure! forward unauthor.jsp");
		// return new Viewable("/login.jsp");
		// return new Viewable("/login");
		// request.getRequestDispatcher("/WEB-INF/jsp/warn/unauthor.jsp").forward(request,
		// response);
		// jersey的跳转到一个地方
		return new Viewable("/WEB-INF/jsp/warn/unauthor");
//		ResponseEnum resEnum = ResponseEnum.AUTHOR_FAILURE;
//		return new ResponseResult(resEnum.getCode(), resEnum.getMsg());
	}

	/*
	 * 认证失败
	 * 
	 * @param request 请求
	 * 
	 * @param response 响应
	 * 
	 * @return ResponseResult
	 * 
	 * @GET
	 * 
	 * @Path("/unauthen")
	 * 
	 * @Consumes({ MediaType.APPLICATION_XML,
	 * MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	 * 
	 * @Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON }) // public
	 * Viewable forwardUnauthen(@Context HttpServletResponse response) { public
	 * ResponseResult forwardUnauthen(@Context HttpServletRequest
	 * request, @Context HttpServletResponse response) { // LOG.info(
	 * "authen failure! forward unauthen.jsp"); // LOG.info(
	 * "authen failure! redirt login.jsp"); // 如果是ajax请则返回XMLHttpRequest String
	 * requestType = request.getHeader("X-Requested-With"); LOG.info(
	 * "is ajax request :" + requestType); // 非ajax请求,未登录或登录失效时,重定向登录页面 //
	 * 好处是非ajax请求时,不返回json数据 if (requestType == null) { try { LOG.info(
	 * "authen failure! redirt login.jsp");
	 * response.sendRedirect("<%=request.getContextPath()%>/dlc/user/login");
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } return null; } // 跳转登录页面
	 * // return new Viewable("/WEB-INF/login/login"); // 转发未登录提示页面 // return
	 * new Viewable("/WEB-INF/jsp/warn/unauthen"); // ajax请求,未登录或登录失效时,返回json //
	 * 这用做的好处是对于ajax的前端页面按钮,在未登录的情况下可以,依旧可以返回json数据 LOG.info(
	 * "authen failure! return  AUTHEN_FAILURE"); ResponseResult reslut;
	 * ResponseEnum resEnum; // 登录失效 resEnum = ResponseEnum.AUTHEN_FAILURE;
	 * reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg()); return
	 * reslut; }
	 */

}
