package com.prayer.live.living.provider.service;

import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-26 19:44
 **/
public interface LivingRoomTxService {
	boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO);
}
