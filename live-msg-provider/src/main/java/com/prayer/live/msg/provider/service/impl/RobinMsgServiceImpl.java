package com.prayer.live.msg.provider.service.impl;

import com.prayer.live.msg.dto.LivingRoomScDTO;
import com.prayer.live.msg.dto.MessageDTO;
import com.prayer.live.msg.provider.dao.mapper.SuperChatRecordMapper;
import com.prayer.live.msg.provider.dao.po.SuperChatRecordPO;
import com.prayer.live.msg.provider.service.RobinMsgService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-15 13:31
 **/
@Service
public class RobinMsgServiceImpl implements RobinMsgService {
	@Resource
	private SuperChatRecordMapper chatRecordMapper;
	@Override
	public LivingRoomScDTO queryNewSc(Long id, Integer roomId) {
		List<SuperChatRecordPO> superChatRecordPOS = chatRecordMapper.queryNewSc(id, roomId);
		List<MessageDTO> res = new ArrayList<>();
		for(SuperChatRecordPO sc : superChatRecordPOS){
			MessageDTO msg = new MessageDTO();
			msg.setContent(sc.getContent());
			msg.setUserId(sc.getUserId());
			msg.setRoomId(sc.getRoomId());
			msg.setType(2);
			res.add(msg);
		}
		LivingRoomScDTO livingRoomScDTO = new LivingRoomScDTO();
		livingRoomScDTO.setRespDTO(new ArrayList<>(res));
		return livingRoomScDTO;
	}
}
