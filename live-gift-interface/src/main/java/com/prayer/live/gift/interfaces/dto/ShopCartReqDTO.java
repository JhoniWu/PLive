package com.prayer.live.gift.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:31
 **/
public class ShopCartReqDTO implements Serializable {

	private static final long serialVersionUID = -6484990398409921247L;

	private Long userId;
	private Long skuId;
	private Integer roomId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
}
