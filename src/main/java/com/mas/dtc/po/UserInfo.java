package com.mas.dtc.po;

import java.io.Serializable;

/**
 * 用户信息的实体类
 * (1)用于用户登录
 * (2)提高给前端用来展示用户信息,和前端的页面权限控制
 * @author Mathsys
 *
 */
public class UserInfo implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8448351149294416111L;
	

	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 用户ip
	 */
	private String ip;
	
	/**
	 * 帐号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String userName;
	
	/**
	 * 帐号状态
	 */
	private String status;
	/**
	 * 角色id
	 */
	private Integer roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	
	public Integer getId() {
		return userId;
	}
	public void setId(Integer userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", account=" + account + ", password=" + password + ", userName="
				+ userName + ", status=" + status + ", roleId=" + roleId + ", roleName=" + roleName + "]";
	}
	
}
