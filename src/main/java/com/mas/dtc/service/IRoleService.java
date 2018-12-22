package com.mas.dtc.service;


import com.mas.dtc.vo.ResponseResult;

/**
 * 角色业务层接口
 * @author Mathartsys
 *
 */
public interface IRoleService {

	/**
	 * 获取所有角色信息
	 * @return 角色信息
	 */
	public ResponseResult getAllRoles() ;
	

	/**
	 * 根据角色id获取角色名
	 * @param roleId 角色id
	 * @return 角色名
	 */
	public String getRoleInfoByRoleName(Integer roleId) ;

}
