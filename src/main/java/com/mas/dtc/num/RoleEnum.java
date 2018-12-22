package com.mas.dtc.num;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 角色枚举
 * 
 * @author Mathsys
 *
 */
public enum RoleEnum {
	/**
	 * 管理员角色
	 */
	ADMIN(0,"admin","管理员"),
	/**
	 * 用户角色
	 */
	USER(1,"user","用户");
	
	/**
	 * 用于存放roleid,roleName,roleDesc的集合
	 */
	private Map<String,Object> role = new HashMap<String,Object>();
	
	/**
	 * 角色代码
	 */
	private Integer roleId ; 
	/**
	 * 角色名称
	 */
	private String roleName ; 
	/**
	 * 角色描述
	 */
	private String roleDesc ; 
	
	/**
	 * 构造方法
	 * @param roleId 角色代码
	 * @param roleName 角色名称 
	 * @param roleDesc 角色描述
	 */
	RoleEnum(Integer roleId,String roleName,String roleDesc){
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.role.put("roleId", roleId);
		this.role.put("roleName", roleName);
		this.role.put("roleDesc", roleDesc);
	}
	
	public  Map<String,Object> getRole(){
		return this.role;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}

	
}
