package com.prayer.live.gift.interfaces.interfaces;

import com.prayer.live.gift.interfaces.dto.GiftRecordDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 20:24
 **/
public interface IGiftRecordRpc {
	/**
	 * 插入单个礼物信息
	 *
	 * @param giftRecordDTO
	 */
	void insertOne(GiftRecordDTO giftRecordDTO);
}
