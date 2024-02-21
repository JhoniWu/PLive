package com.prayer.live.framework.web.starter.context;

import com.prayer.live.framework.web.starter.config.RequestLimit;
import com.prayer.live.framework.web.starter.error.LiveErrorException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-20 14:45
 **/
public class RequestLimitInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitInterceptor.class);

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Value("${spring.application.name}")
	private String applicationName;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		boolean hasLimit = handlerMethod.getMethod().isAnnotationPresent(RequestLimit.class);
		if(hasLimit){
			//判断是否需要限制
			RequestLimit requestLimit = handlerMethod.getMethod().getAnnotation(RequestLimit.class);
			Long userId = LiveRequestContext.getUserId();
			if(userId == null){
				return true;
			}
			String requestKey = applicationName + ":" + request.getRequestURI() + ":" + userId;
			int limit = requestLimit.limit();
			int second = requestLimit.second();
			Integer reqTime = (Integer) Optional.ofNullable(redisTemplate.opsForValue().get(requestKey)).orElse(0);
			if(reqTime == 0){
				redisTemplate.opsForValue().increment(requestKey, 1);
				redisTemplate.expire(requestKey, second, TimeUnit.SECONDS);
				return true;
			} else if(reqTime < limit) {
				redisTemplate.opsForValue().increment(requestKey,1);
				return true;
			}
			//直接抛出全局异常，让异常捕获器处理
			LOGGER.error("[RequestLimitInterceptor] userId is {},req too much", userId);
			throw new LiveErrorException(-1, requestLimit.msg());
		} else {
			return true;
		}
	}
}
