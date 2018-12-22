package com.mas.dtc.controller;


import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
//import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Controller;

import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.ICrudUserService;
import com.mas.dtc.service.IRoleService;
import com.mas.dtc.vo.ResponseResult;

/**
 * 用户管理
 * 
 * @author Mathsys
 *
 */
@Path("/dlc/cruduser")
@Controller
public class CrudUserController {

//	/**
//	 * 日志
//	 */
//	private static final Logger LOG = Logger.getLogger(CrudUserController.class);

	/**
	 * 用户添加业务层
	 */
	@Resource(name = "crudUserServiceImpl")
	private ICrudUserService crudUserServiceImpl;

	/**
	 * 角色业务层
	 */
	@Resource(name = "roleServiceImpl")
	private IRoleService roleServiceImpl;

	/**
	 * 用户管理列表
	 * 
	 * @return Viewable
	 * 
	 */
	@GET
	@Path("/usermanagement")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Viewable forwardUserPag() {
//		LOG.info("forward userAuth.jsp");
		return new Viewable("/WEB-INF/userManagement/userManagement");
	}

	/**
	 * 用户管理列表
	 * 
	 * @return json 格式 "data":[ { "userId" : 2, "account" : "ycy", "userName" :
	 *         "赵越", "status" : "0", "roleId" : 0, "password":"ycy", "roleName"
	 *         : "admin" }]
	 */
	@GET
	@Path("/getuserbyall")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult queryUserByAll() {
		// 获取当前Subject 
		Subject subject = SecurityUtils.getSubject();
		UserInfo userInfo = (UserInfo) subject.getPrincipal();
		String account = userInfo.getAccount();
		return crudUserServiceImpl.queryUserByAll(account);
	}

	/**
	 * 角色
	 * 
	 * @return json格式: 例如 "data":[ { "roleId" : 0, "roleName" : "admin",
	 *         "roleDesc" : "管理员" }, { "roleId" : 1, "roleName" : "user",
	 *         "roleDesc" : "用户" } ]
	 */
	@GET
	@Path("/getrole")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult getRole() {
		return roleServiceImpl.getAllRoles();
	}

	/**
	 * 
	 * 添加用户 
	 * jersey会将前端请求自动转化为javabean对象接收请求参数
	 *  @param userInfo 用户信息
	 * @return {"header":{}"code":2000,"msg":"用户添加成功"}
	 * 
	 *
	 */
	@POST
	@Path("/adduser")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult addUser(UserInfo userInfo) {
		return crudUserServiceImpl.addUser(userInfo);
	}

	/**
	 * 
	 * 用户信息编辑
	 * @param userInfo 用户信息
	 * @return {"header":{},"code":2001,"msg":"用户编辑成功"}
	 * 
	 */
	@PUT
	@Path("/updateuser")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult updateUser(UserInfo userInfo) {
		return  crudUserServiceImpl.updateUser(userInfo);
	}

}
