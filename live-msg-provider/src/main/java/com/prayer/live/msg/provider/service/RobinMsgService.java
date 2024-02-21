package com.prayer.live.msg.provider.service;

import com.prayer.live.msg.dto.LivingRoomScDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-15 13:23
 **/
public interface RobinMsgService {
	LivingRoomScDTO queryNewSc(Long id, Integer roomId);
}
