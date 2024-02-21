package com.prayer.live.im.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.im.constants.ImMsgCodeEnum;
import com.prayer.live.im.core.server.common.ChannelHandlerContextCache;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.core.server.service.IMsgAckCheckService;
import com.prayer.live.im.core.server.service.RouterHandlerService;
import com.prayer.live.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 18:07
 **/
@Service
public class RouterHandlerServiceImpl implements RouterHandlerService {
	Logger LOGGER = LoggerFactory.getLogger(RouterHandlerServiceImpl.class);
	@Resource
	private IMsgAckCheckService msgAckCheckService;
	@Override
	public void onReceive(ImMsgBody imMsgBody) {

		//需要进行消息通知的userid
		Long userId = imMsgBody.getUserId();
		LOGGER.info("im-core接收到消息，准备发给user{}",userId);
		if (sendMsgToClient(imMsgBody)) {
			msgAckCheckService.recordMsgAck(imMsgBody, 1);
			msgAckCheckService.sendDelayMsg(imMsgBody);
		}
	}

	@Override
	public boolean sendMsgToClient(ImMsgBody imMsgBody) {
		Long userId = imMsgBody.getUserId();
		ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
		if(ctx!=null){
			String msgId = UUID.randomUUID().toString();
			imMsgBody.setMsgId(msgId);
			ImMsg resMsg = ImMsg.build(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), JSON.toJSONString(imMsgBody));
			ctx.writeAndFlush(resMsg);
			return true;
		}
		return false;
	}
}