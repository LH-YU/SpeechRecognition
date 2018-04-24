package com.speech.dbutil;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import com.speech.dbutil.DataSource;
import com.speech.dbutil.JdbcContextHolder;

public class DataSourceAspectModel implements  AfterReturningAdvice,MethodBeforeAdvice{
	static Logger logger = LoggerFactory.getLogger(DataSourceAspectModel.class);

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		JdbcContextHolder.clearJdbcType();
	}

	public void before(Method method, Object[] args, Object target) throws Throwable {
		DataSource d = method.getAnnotation(DataSource.class);
		if(d != null) {
			JdbcContextHolder.setJdbcType(d.value());
		} 
		
	}
	
	
	
}