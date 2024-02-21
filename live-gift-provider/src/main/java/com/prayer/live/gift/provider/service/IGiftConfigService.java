package com.prayer.live.gift.provider.service;

import com.prayer.live.gift.interfaces.dto.GiftConfigDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:27
 **/
public interface IGiftConfigService {

	/**
	 * 按照礼物id查询
	 *
	 * @param giftId
	 * @return
	 */
	GiftConfigDTO getByGiftId(Integer giftId);

	/**
	 * 查询所有礼物信息
	 *
	 * @return
	 */
	List<GiftConfigDTO> queryGiftList();

	/**
	 * 插入单个礼物信息
	 *
	 * @param giftConfigDTO
	 */
	void insertOne(GiftConfigDTO giftConfigDTO);

	/**
	 * 更新单个礼物信息
	 *
	 * @param giftConfigDTO
	 */
	void updateOne(GiftConfigDTO giftConfigDTO);
}
