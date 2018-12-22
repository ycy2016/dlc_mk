package com.mas.dtc.po;

/**
 * 
 * 日志实体类
 * 
 * @author Mathsys
 *
 */
public class LogInfo {

	/**
	 * id
	 */
	private Integer logInfoId;

	/**
	 * 帐号
	 */
	private String account;

	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 操作
	 */
	private String operation;

	/**
	 * 操作结果信息
	 */
	private String msg;

	/**
	 * 日志时间
	 */
	private String createtime;

	/**
	 * 构造器
	 * @param account 帐号
	 * @param ip ip地址
	 * @param operation 操作动作
	 * @param msg  操作结果信息
	 */
	public  LogInfo(String account, String ip, String operation, String msg) {
		this.account=account;
		this.ip=ip;
		this.operation = operation;
		this.msg = msg;
	}

	public Integer getLogInfoId() {
		return logInfoId;
	}

	public void setLogInfoId(Integer logInfoId) {
		this.logInfoId = logInfoId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "LogInfo [logInfoId=" + logInfoId + ", account=" + account + ", ip=" + ip + ", operation=" + operation
				+ ", msg=" + msg + ", createtime=" + createtime + "]";
	}

}
