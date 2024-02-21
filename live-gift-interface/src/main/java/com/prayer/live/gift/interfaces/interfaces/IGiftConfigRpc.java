package com.prayer.live.gift.interfaces.interfaces;

import com.prayer.live.gift.interfaces.dto.GiftConfigDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 20:24
 **/
public interface IGiftConfigRpc {
	/**
	 * 获取礼物信息
	 * @param giftId
	 * @return
	 */
	GiftConfigDTO getByGiftId(Integer giftId);

	/**
	 * 查询所有礼物信息
	 * @return
	 */
	List<GiftConfigDTO> queryGiftList();

	/**
	 * 插入单个礼物信息
	 * @param giftConfigDTO
	 */
	void insertOne(GiftConfigDTO giftConfigDTO);

	/**
	 * 更新单个礼物信息
	 * @param giftConfigDTO
	 */
	void updateOne(GiftConfigDTO giftConfigDTO);

}
