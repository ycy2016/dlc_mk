package com.mas.dtc.dao;

import org.apache.ibatis.annotations.Param;

import com.mas.dtc.po.UserInfo;

/**
 * 查询用户
 * (1) 登录使用
 * (2)获取 用户信息(用于登录)
 * @author Mathsys
 *
 */
public interface IUserInfoDAO {
	/**
	 *  判断帐号和密码是否一致
	 * @param account 帐号
	 * @param password 密码 
	 * @return 用户实例
	 */
	public UserInfo getUserFindbyNameAndPassword(@Param("account") String account,@Param("password") String password);
	
	/**
	 * 判断帐号和密码是否一致
	 * @param account 帐号
	 * @return 用户实例
	 */
	public UserInfo getUserFindbyName(@Param("account") String account);
	
}
