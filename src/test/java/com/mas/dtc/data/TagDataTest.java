package com.mas.dtc.data;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.mas.dtc.service.IDataService;

/**
 * 产品看板测试类
 * @author Mathsys
 *
 */
public class TagDataTest {

	/**
	 * 加载spring容器
	 */
	private static ClassPathXmlApplicationContext  ctx;
	
	@BeforeSuite()
	public void loadContext(){
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	/**
	 * 接口数据
	 */
	@Test
	public void text1(){
		
		
		IDataService dataServiceImpl = ctx.getBean("dataServiceImpl",IDataService.class);
		
		System.out.println(JSON.toJSONString(dataServiceImpl.getInterfaceByAll()));
	}
	
	/**
	 * 标签详情
	 */
	@Test
	public void text2(){
		
		
		IDataService dataServiceImpl = ctx.getBean("dataServiceImpl",IDataService.class);
		
		System.out.println(JSON.toJSONString(dataServiceImpl.getInterfaceById("A1")));
	}
	
}
