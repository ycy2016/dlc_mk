package com.mas.dtc.framework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * 
 * 被LogAopAnnoation注解的方法会被代理
 * 
 * @author Mathsys
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface LogAnnoation {
	
	/**
	 * 方法对应的操作名称
	 * 
	 * @return  操作名称
	 */
	String operation() default "";
}
