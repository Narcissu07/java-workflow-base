package com.tr.workflow.api.aspect;

import com.tr.common.framework.helper.AspectHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpAspect {
	private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	
	@Around("execution(* com.tr.workflow.api.controller..*.*(..))")
	public Object apiAspect(ProceedingJoinPoint jp) throws Throwable {
		return AspectHelper.apiAspect(jp, logger);
	}
	
	@Around("execution(* com.tr.common.framework.impl..*.*(..))")
	public Object clientAspect(ProceedingJoinPoint jp) throws Throwable {
		return AspectHelper.clientAspect(jp, logger);
	}
}
