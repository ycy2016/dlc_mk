package com.mas.dtc.application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * jersey 注册类
 * @author Mathsys
 *
 */
@Deprecated
public class RestApplication extends ResourceConfig {

	/**
	 * RestApplication构造器
	 */
	public RestApplication() {
//		packages("com.mas.dtc");
//		register(JacksonJsonProvider.class);
//		// JspMvcFeature是用来描述要使用Jersery MVC这个Feature，并且已JSP当作Template Engine
//		register(JspMvcFeature.class);
//		register(MultiPartFeature.class);
//		// JspMvcFeature.TEMPLATES_BASE_PATH是用来描述JSP的root的路径,"/""代表根路径为webapp
//		property(JspMvcFeature.TEMPLATE_BASE_PATH, "/");
	}

}
