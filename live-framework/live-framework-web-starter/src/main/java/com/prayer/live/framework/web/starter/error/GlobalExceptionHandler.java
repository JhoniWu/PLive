package com.prayer.live.framework.web.starter.error;

import com.prayer.live.common.interfaces.vo.WebResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-20 13:31
 **/
@Configuration
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public WebResponseVO errorHandler(HttpServletRequest request, Exception e){
		LOGGER.error(request.getRequestURI() + "error is ", e);
		return WebResponseVO.sysError("系统异常");
	}

	@ExceptionHandler(value = LiveErrorException.class)
	@ResponseBody
	public WebResponseVO sysErrorhandler(HttpServletRequest request, LiveErrorException e){
		LOGGER.error(request.getRequestURI() + ", error code is {}, error msg is {}", e.getErrorCode(), e.getErrorMsg());
		return WebResponseVO.bizError(e.getErrorMsg());
	}
}

