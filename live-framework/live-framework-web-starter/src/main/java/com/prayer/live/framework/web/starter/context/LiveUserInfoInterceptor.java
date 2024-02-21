package com.prayer.live.framework.web.starter.context;

import com.prayer.live.common.interfaces.enums.GatewayHeaderEnum;
import com.prayer.live.framework.web.starter.constants.RequestConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-23 21:16
 **/
public class LiveUserInfoInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
		String userId = request.getHeader(GatewayHeaderEnum.USER_LOGIN_ID.getName());
		if(StringUtils.isEmpty(userId)){
			return true;
		}
		LiveRequestContext.set(RequestConstants.PRAYER_LIVE_ID, Long.valueOf(userId));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		LiveRequestContext.clear();
	}
}
