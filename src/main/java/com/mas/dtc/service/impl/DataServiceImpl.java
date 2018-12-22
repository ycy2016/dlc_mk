package com.mas.dtc.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mas.dtc.data.TagMapping;
import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.po.MappingFileInfo;
import com.mas.dtc.service.IDataService;
import com.mas.dtc.vo.ResponseResult;

/**
 * 获取看板数据的service实现
 * 
 * @author Mathsys
 *
 */
@Service(value = "dataServiceImpl")
public class DataServiceImpl implements IDataService {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(DataServiceImpl.class);

	/**
	 * 依赖注入
	 */
	@Resource(name = "tagMapping")
	private TagMapping tagMapping;

	/**
	 * mapping表上传和存放路径
	 */
	@Value("${dlc.master.conf}")
	private String basePath;

	/**
	 * 读写锁
	 */
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	@Override
	public ResponseResult uploadMapping(FormDataBodyPart filePart, String account, String ip) {
		ResponseResult reslut = null;
		ResponseEnum resEnum = null;
		// 解析文件标识
		int isParesed = 0;
		
		InputStream fileInputStream = null;
		try {
			// 将文件流数据转换成流
		    fileInputStream = filePart.getValueAs(InputStream.class);
			FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
			// 得到文件名
			String fileName = formDataContentDisposition.getFileName();
			// 解决文件名乱码问题
			fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
			String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + System.currentTimeMillis()
					+ ".xlsx";
			String filePath = this.basePath + File.separator + newFileName;
			File file = new File(filePath);
			System.out.println("路径============>" + filePath);
			boolean isCreate = file.createNewFile();
			if (!isCreate) {
				LOG.info(ip + " \"" + account + "\"  upload mapping has failured, beaceuse can`t build newfile  "
						+ filePath);
			} else {
				// 复制文件到指定路径
				FileUtils.copyInputStreamToFile(fileInputStream, file);
				LOG.info(ip + " \"" + account + "\"  upload mapping has finished, newfilePath is " + filePath);
				MappingFileInfo mappingFileInfo = new MappingFileInfo(ip, account, newFileName);
				// 上写锁(系统只允许同一个时刻,只有一个同步数据的行为)
				this.rwl.writeLock().lock();
				/*
				 * 测试写锁是否有用
			 	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int i = 1 / 0;
				 */
				// 成功的话把isParesed设置为1
				isParesed = tagMapping.uploadMapping(mappingFileInfo);

			}
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				//关闭流
				if(fileInputStream!=null){
					fileInputStream.close();
				}
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
			// 释放写锁
			this.rwl.writeLock().unlock();
		}

		if (isParesed == 1) {
			resEnum = ResponseEnum.PARSE_SUCCESS;
			LOG.info("PARSE mapping file success!");
		} else {
			resEnum = ResponseEnum.PARSE_FAILURE;
		}

		reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg());
		return reslut;
	}

	@Override
	public ResponseResult getInterfaceByAll() {

		// 上读锁
//		LOG.info("getInterfaceByAll()... waitting read");
		this.rwl.readLock().lock();
//		LOG.info("getInterfaceByAll()... is reading");
		List<Map<String, String>> list = null;
		try {
			list = tagMapping.getInterfaceDescList();
		} finally {
			// 释放读锁
			this.rwl.readLock().unlock();
		}
		ResponseEnum resEnum;
		if (list != null && list.size() > 0) {
			resEnum = ResponseEnum.QUERY_SUCCESS;
		} else {
			resEnum = ResponseEnum.QUERY_FAILURE;
		}
		Map<String, Object> header = new HashMap<>();
		header.put("data", list);
		return new ResponseResult(resEnum.getCode(), resEnum.getMsg(), header);
	}

	@Override
	public ResponseResult getInterfaceById(String interfaceid) {
		ResponseEnum resEnum;
		// 上读锁
		this.rwl.readLock().lock();
		List<Map<String, Object>> list;
		try {
			list = tagMapping.getTagByInterfaceId(interfaceid);
		} finally {
			// 释放读锁
			this.rwl.readLock().unlock();
		}
		if (list != null && list.size() > 0) {
			resEnum = ResponseEnum.QUERY_SUCCESS;
		} else {
			resEnum = ResponseEnum.QUERY_FAILURE;
		}
		Map<String, Object> header = new HashMap<>();
		header.put("data", list);
		return new ResponseResult(resEnum.getCode(), resEnum.getMsg(), header);
	}

}
