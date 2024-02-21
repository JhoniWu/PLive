package com.prayer.live.msg.provider.consumer.handler.impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.im.constants.AppIdEnum;
import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.im.router.interfaces.rpc.ImRouterRpc;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.rpc.ILivingRoomRpc;
import com.prayer.live.msg.dto.MessageDTO;
import com.prayer.live.msg.enums.ImMsgBizCodeEnum;
import com.prayer.live.msg.provider.consumer.handler.MessageHandler;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:46
 **/
@Component
public class SingleMessageHandler implements MessageHandler {
	Logger LOGGER = LoggerFactory.getLogger(SingleMessageHandler.class);
	@DubboReference
	private ImRouterRpc routerRpc;
	@DubboReference
	private ILivingRoomRpc livingRoomRpc;
	@Override
	public void onMsgReceive(ImMsgBody imMsgBody) {
		LOGGER.info("消息处理开始...");
		int bizCode = imMsgBody.getBizCode();
		if(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode() == bizCode){
			MessageDTO messageDTO = JSON.parseObject(imMsgBody.getData(), MessageDTO.class);
			Integer roomId = messageDTO.getRoomId();
			LivingRoomReqDTO reqDTO = new LivingRoomReqDTO();
			reqDTO.setRoomId(roomId);
			reqDTO.setAppId(imMsgBody.getAppId());
			//List<Long> userIdList = livingRoomRpc.queryUserIdByRoomId(reqDTO).stream().collect(Collectors.toList());
			List<Long> userIdList = new ArrayList<>();
			userIdList.add(10000L);
			if(CollectionUtils.isEmpty(userIdList)){
				return ;
			}

			List<ImMsgBody> imMsgBodyList = new ArrayList<>();
			userIdList.forEach(userId -> {
				ImMsgBody respMsg = new ImMsgBody();
				respMsg.setUserId(userId);
				respMsg.setAppId(AppIdEnum.LIVE_BIZ.getCode());
				respMsg.setBizCode(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode());
				respMsg.setData(JSON.toJSONString(messageDTO));
				imMsgBodyList.add(respMsg);
			});
			//暂时不做过多的处理
			LOGGER.info("处理消息中... 准备发送");
			routerRpc.batchSendMsg(imMsgBodyList);
		}
		LOGGER.info("消息处理失败...");
	}
}
