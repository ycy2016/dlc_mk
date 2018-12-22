package com.mas.dtc.dao;

import com.mas.dtc.po.LogInfo;

/**
 * 日志接口
 * 
 * @author Mathsys
 *
 */
public interface ILogDAO {

	/**
	 * 插入日志
	 * @param logInfo 日志对象
	 * @return int 插入的记录是否成功 1:代表成功
	 */
	public int insertLog(LogInfo logInfo);
	
}
