package com.prayer.live.im.core.server.handler.Impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.im.core.server.common.ImContextUtils;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.handler.SimplyHandler;
import com.prayer.live.im.core.server.service.IMsgAckCheckService;
import com.prayer.live.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 19:33
 **/
@Component
public class AckImMsgHandler implements SimplyHandler {
	private static final Logger logger = LoggerFactory.getLogger(AckImMsgHandler.class);

	@Resource
	private IMsgAckCheckService msgAckCheckService;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		if(userId == null || appId == null) {
			ctx.close();
			throw new IllegalArgumentException("attr is error");
		}
		msgAckCheckService.doMsgAck(JSON.parseObject(imMsg.getBody(),  ImMsgBody.class));
	}
}