package com.mas.dtc.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mas.dtc.service.impl.DataServiceImpl;
//import com.mas.dtc.utils.IpUtil;
import com.mas.dtc.vo.ResponseResult;

/**
 *获取数据
 * (1)获取数据接口信息
 * (2)获取标签信息
 * @author Mathsys
 *
 */
@Path("/dlc/interface")
@Component
public class DataController {

//	/**
//	 * 日志
//	 */
//	private static final Logger LOG = Logger.getLogger(DataController.class);

	/**
	 * DataServiceImpl
	 */
	@Resource(name="dataServiceImpl")
	private DataServiceImpl dataServiceImpl;

	/**
	 * 获取所有的接口数据 
	 * @param request 请求
	 * @return json格式
	 */
	@GET
	@Path("/getinterfacebyall")
	@Consumes({  MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseResult getInterfaceByAll() {
//		String ip = IpUtil.getIpAddr(request);
//		LOG.info(ip + " visit interface data " );
		return  dataServiceImpl.getInterfaceByAll();
	}
	
	/**
	 * 通过接口代码获取标签数据
	 * @param request 请求实体
	 * @param interfaceid 接口数据代码
	 * @return json格式的响应
	 */
	@GET
	@Path("/gettagbyinterfaceid")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces({MediaType.TEXT_HTML,MediaType.APPLICATION_JSON})
	public ResponseResult getTagByInterfaceId(@Context HttpServletRequest request, @QueryParam("interface_id")String interfaceid) {
//		String ip = IpUtil.getIpAddr(request);
//		LOG.info(ip + " visit tag data "+  interfaceid);
		return  dataServiceImpl.getInterfaceById(interfaceid);
	}
	
}
