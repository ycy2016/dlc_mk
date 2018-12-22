package com.mas.dtc.dao;

import com.mas.dtc.po.MappingFileInfo;

/**
 * mapping文件 业务层
 * @author Mathsys
 *
 */
public interface IMappingFileDAO {

	/**
	 * 获取最近一次解析成功的mapping文件名称
	 * @return mapping文件名
	 */
	public String getRecentlyFileName();
	
	/**
	 * 
	 * 插入上传的文件信息 
	 * @param fileinfo mapping文件的实例
	 * @return 插入数据的条数
	 */
	public int insertFileinfo(MappingFileInfo fileinfo);
	
	
}
