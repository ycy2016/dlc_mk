package com.mas.dtc;


import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.ICrudUserService;
import com.mas.dtc.vo.ResponseResult;
/**
 * 用户管理单元测试
 * @author Mathartsys
 *
 */
public class CrudUserTest {
	/**
	 * 日志
	 */
	private static final Logger LOG=Logger.getLogger(CrudUserTest.class);
	
	/**
	 * 加载spring容器
	 */
	private  ClassPathXmlApplicationContext ctx = null;

	/**
	 * 用户业务层处理类
	 */
	private ICrudUserService crudUserService= null;

	@BeforeMethod
	public void init(){
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		crudUserService= (ICrudUserService) ctx.getBean("crudUserServiceImpl");
	}

	/**
	 * 添加用户信息
	 * @Test(enabled = false)禁用此测试案例。
	 */
	@Test(enabled = false)
	public void testAdd() {
		
		UserInfo userInfo=new UserInfo();
		userInfo.setAccount("user123");
		userInfo.setPassword("user123");
		userInfo.setUserName("user123");
		userInfo.setRoleId(0);
		userInfo.setRoleName("admin");

		ResponseResult addInfo = crudUserService.addUser(userInfo);
		LOG.info("add result : "+addInfo);

	}
	/**
	 * 修改用户信息
	 */
	@Test
	public void testUpdate() {

		UserInfo userInfo=new UserInfo();
		userInfo.setAccount("user123");
		userInfo.setPassword("user123");
		userInfo.setUserName("user123");
		userInfo.setRoleId(1);
		userInfo.setRoleName("user");
		ResponseResult updateInfo = crudUserService.updateUser(userInfo);
		LOG.info("update result : "+updateInfo);
		
	}
	/**
	 * 修改用户信息
	 */
	@Test
	public void testSelectAllUser() {
		String account  = "ycy";
		ResponseResult selectInfo = crudUserService.queryUserByAll(account);
		LOG.info("select result : "+selectInfo);
		
	}
}
