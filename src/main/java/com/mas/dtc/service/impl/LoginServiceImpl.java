package com.mas.dtc.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mas.dtc.dao.IUserInfoDAO;
import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.ILoginService;

/**
 * 
 * 登录
 * 获取用户信息
 * @author Mathsys
 *
 */
@Service(value = "loginServiceImpl")
public class LoginServiceImpl implements ILoginService {
	
	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(LoginServiceImpl.class);
	
	/**
	 * 用户操作DAO
	 */
	@Resource
	private IUserInfoDAO userInfoDAO;
	
	@Override
	public UserInfo getUserByAccountAndPass(String account,String password){
		UserInfo userinfo = null;
		try{
			 userinfo = userInfoDAO.getUserFindbyNameAndPassword(account,password);
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return userinfo;
	}
	
	@Override
	public UserInfo getUserFindbyAccount(String account){
		UserInfo userinfo = null;
		try{
			 userinfo = userInfoDAO.getUserFindbyName(account);
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return userinfo;
	}
}
