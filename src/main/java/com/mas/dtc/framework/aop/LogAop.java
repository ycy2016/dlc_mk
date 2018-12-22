package com.mas.dtc.framework.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.mas.dtc.dao.ILogDAO;
import com.mas.dtc.framework.aop.annotation.LogAnnoation;
import com.mas.dtc.po.LogInfo;
import com.mas.dtc.po.UserInfo;
import com.mas.dtc.utils.IpUtil;
import com.mas.dtc.vo.ResponseResult;

/**
 * 定义的切面
 * 
 * @author Mathsys
 *
 */
@Aspect
@Component(value = "logAop")
public class LogAop {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(LogAop.class);
	
	/**
	 * 日志DAO
	 */
	@Resource(name ="ILogDAO")
	private ILogDAO logDao ;
	
	

	/**
	 * 方法正常返回时,执行的的方法, 如果方法抛异常不会执行
	 * 
	 * @param jPoint
	 *            spring aop提供的方法 实例
	 * @param returnValue
	 *            方法被切方法返回的结果
	 */
	@AfterReturning(value = "@annotation(com.mas.dtc.framework.aop.annotation.LogAnnoation)", returning = "returnValue")
	public void doAfterReturn(JoinPoint jPoint, Object returnValue) {

		// 为了获取方法上的注解
		Signature signature = jPoint.getSignature();
		String signatureName = signature.getName();
//		LOG.info("aop doAfterReturn " + signatureName + " Method...");
		MethodSignature methodSignature = (MethodSignature) signature;
		Method targetMethod = methodSignature.getMethod();

		// 访问目标方法的参数：
		Object[] args = jPoint.getArgs();
		HttpServletRequest request = null;

		for (Object arg : args) {
			if (arg instanceof ServletRequest) {
				//获取请求实例,为ip获取做准备
				request = (HttpServletRequest) arg;
			}
		}

		try {
			Method realMethod = jPoint.getTarget().getClass().getDeclaredMethod(signatureName,
					targetMethod.getParameterTypes());

			Subject subject = SecurityUtils.getSubject();
			UserInfo userInfo = (UserInfo) subject.getPrincipal();

			// 获取ip
			String ip = null;
			if (userInfo != null) {// 已经登录的用户可以通过userInfo获取ip,
				ip = userInfo.getIp();
			} else if (request != null) {// 未登录的可以使用request实例获取ip
				ip = IpUtil.getIpAddr(request);
			}

			// 获取帐号
			String account = null;
			if (userInfo != null) {
				account = userInfo.getAccount();
			}

			// 获取操作
			String operation = null;
			if (realMethod.isAnnotationPresent(LogAnnoation.class)) {
				LogAnnoation permission = (LogAnnoation) realMethod.getAnnotation(LogAnnoation.class);
				operation = permission.operation();
			}

			// 获取操作结果
//			if()
			ResponseResult responseResult = (ResponseResult) returnValue;
			String msg = responseResult.getMsg();

			LogInfo logInfo = new LogInfo(account,ip,operation,msg);
			
//			LOG.info(logInfo);
			
			/**/
			 logDao.insertLog(logInfo);
			 
		
			
			
		} catch (NoSuchMethodException e) {
			LOG.error(e.getMessage(),e);
		} catch (SecurityException e) {
			LOG.error(e.getMessage(),e);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		
	}

}
