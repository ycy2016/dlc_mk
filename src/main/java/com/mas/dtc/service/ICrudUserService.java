package com.mas.dtc.service;

import java.util.List;

import com.mas.dtc.po.UserInfo;
import com.mas.dtc.vo.ResponseResult;


/**
 * 用户信息业务层接口
 * @author Mathartsys
 *
 */
public interface ICrudUserService {
	/**
	 * 获取用户查询信息
	 * @return 用户查询信息 
	 */
	public ResponseResult queryUserByAll(String account);
	/**
	 * 添加用户
	 * @param userInfo 要添加的用户
	 * @return 用户添加结果信息
	 */
	public ResponseResult addUser(UserInfo userInfo) ;

	/**
	 * 	 根据用户账号获取用户信息
	 * @param userInfo 用户信息
	 * @return 相同用户
	 */
	public List<UserInfo> findUserByAccount(UserInfo userInfo) ;
	
	/**
	 * 用户信息更新
	 * @param userInfo 用户信息
	 * @return 更新操作信息
	 */
	public ResponseResult updateUser(UserInfo userInfo) ;
}
