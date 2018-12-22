package com.mas.dtc.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.stereotype.Controller;

import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.IDataService;
import com.mas.dtc.vo.ResponseResult;

/**
 * 同步或者获取数据 (1)上传mapping
 * 
 * @author Mathsys
 *
 */
@Path("/dlc/mapping")
@Controller
public class MappingController {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(MappingController.class);

	/**
	 * 数据同步模块service
	 */
	@Resource(name = "dataServiceImpl")
	private IDataService dataServiceImpl;

	/**
	 * 
	 * 文件上传接口
	 * 
	 * @param formFormDataMultiPart
	 *            多媒体实例
	 * @param request
	 *            请求实例
	 * @param response
	 *            响应实例
	 * @return json格式返回,响应体为PARSE_SUCCESS或者为PARSE_FAILURE
	 */
	@PUT
	@Path("/upload")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public ResponseResult uploadMapping( FormDataMultiPart formFormDataMultiPart, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		UserInfo userInfo = (UserInfo) subject.getPrincipal();
		String account = userInfo.getAccount();
		String ip = userInfo.getIp();
		// 获取到上传文件的名字
		FormDataBodyPart filePart = formFormDataMultiPart.getField("file");
		LOG.info( ip+" , "+ account + " is uploadMapping");
		ResponseResult reslut = dataServiceImpl.uploadMapping(filePart, account, ip);
		return reslut;
	}

}
