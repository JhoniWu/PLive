package com.prayer.live.api.utils.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-18 20:04
 **/
@Component
@Aspect
@Order(10)
public class LogAspect {
	Logger logger = LoggerFactory.getLogger(LogAspect.class);
	@Pointcut("execution(* com.prayer.live.api.controller.*.*(..))  || @annotation(com.prayer.live.api.utils.annotation.LogAnnotation)")
	public void pointCut(){
	}
	@Around("pointCut()")
	public Object doLog(ProceedingJoinPoint proceedingJoinPoint){
		long startTime = System.currentTimeMillis();
		Object o = null;
		try {
			o = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			long endTime = System.currentTimeMillis();
			logger.info(proceedingJoinPoint.getSignature().toShortString()+"方法执行了"+(endTime - startTime)+"ms");
		}
		return o;
	}
}
