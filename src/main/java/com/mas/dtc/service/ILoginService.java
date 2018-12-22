package com.mas.dtc.service;

import com.mas.dtc.po.UserInfo;

/**
 * 登录 serivce
 * 
 * @author Mathsys
 *
 */
public interface ILoginService {

	/**
	 * 通过帐号密码获取用户
	 * @param account 帐号
	 * @param password 密码
	 * @return UserInfo 实例
	 */
	public UserInfo getUserByAccountAndPass(String account,String password);
	
	/**
	 * 通过帐号获取用户
	 * @param account 帐号
	 * @return UserInfo 实例
	 */
	public UserInfo getUserFindbyAccount(String account);
}
