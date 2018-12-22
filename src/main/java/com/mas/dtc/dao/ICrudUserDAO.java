package com.mas.dtc.dao;

import java.util.List;

import com.mas.dtc.po.UserInfo;

/**
 * 用户操作数据层
 * 
 * @author Mathartsys
 *
 */
public interface ICrudUserDAO {
	/**
	 * 查询获取所有用户的信息
	 * @return 用户信息
	 */
	public List<UserInfo> selectUserByAll();
	/**
	 * 添加用户
	 * @param userInfo 用户信息
	 * @return 1成功 0失败
	 */

	public int insertUser(UserInfo userInfo);
	/**
	 * 根据用户账号，获取用户信息
	 * @param userInfo 用户账号
	 * @return 用户信息
	 */
	public List<UserInfo> selectUserByAccount(UserInfo userInfo);
	/**
	 * 用户信息更新
	 * @param userInfo 用户信息
	 * @return 0 失败 1 成功
	 */
	public int updateUser(UserInfo userInfo);
	

}
