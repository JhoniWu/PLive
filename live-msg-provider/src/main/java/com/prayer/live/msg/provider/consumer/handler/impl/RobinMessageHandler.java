package com.prayer.live.msg.provider.consumer.handler.impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.msg.dto.MessageDTO;
import com.prayer.live.msg.provider.consumer.handler.MessageHandler;
import com.prayer.live.msg.provider.dao.mapper.SuperChatRecordMapper;
import com.prayer.live.msg.provider.dao.po.SuperChatRecordPO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-15 12:53
 **/
@Service
public class RobinMessageHandler implements MessageHandler {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private SuperChatRecordMapper superChatRecordMapper;
	@Override
	public void onMsgReceive(ImMsgBody imMsgBody) {
		MessageDTO msg = JSON.parseObject(imMsgBody.getData(), MessageDTO.class);
		Integer roomId = msg.getRoomId();
		String content = msg.getContent();
		Integer type = msg.getType();
		Long userId = msg.getUserId();
		SuperChatRecordPO sc = new SuperChatRecordPO();
		sc.setRoomId(roomId);
		sc.setContent(content);
		sc.setUserId(userId);
		superChatRecordMapper.insert(sc);
	}
}
