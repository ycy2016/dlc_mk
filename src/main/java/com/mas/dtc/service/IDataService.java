package com.mas.dtc.service;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import com.mas.dtc.vo.ResponseResult;

/**
 * 看板模块 service
 * @author Mathsys
 *
 */
public interface IDataService {

	

	
	/**
	 * 获取所以的接口数据详情
	 * @return ResponseResult
	 */
	public ResponseResult getInterfaceByAll() ;
	
	/**
	 * 通过接口代号获取标签详情
	 * @param interfaceid 对应TagMapField.INTERFACE_ID
	 * @return ResponseResult
	 */
	public ResponseResult getInterfaceById(String interfaceid) ;
	
	/**
	 * 上传文件,同步数据
	 * @param filePart jersey放多媒体文件的实例
	 * @param account 帐号
	 * @param ip ip
	 * @return ResponseResult
	 * 
	 */
	public ResponseResult uploadMapping(FormDataBodyPart filePart, String account, String ip);
	
	/*
	 * 更新Mapping文件
	 * @param mappingFileInfo mapping文件实例
	 * @return ResponseResult
	 
	public ResponseResult uploadMapping(MappingFileInfo mappingFileInfo);
	 */

}
