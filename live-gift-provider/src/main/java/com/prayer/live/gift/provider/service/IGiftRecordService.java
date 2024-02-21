package com.prayer.live.gift.provider.service;

import com.prayer.live.gift.interfaces.dto.GiftRecordDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:27
 **/
public interface IGiftRecordService {
	/**
	 * 插入单个礼物信息
	 *
	 * @param giftRecordDTO
	 */
	void insertOne(GiftRecordDTO giftRecordDTO);
}
