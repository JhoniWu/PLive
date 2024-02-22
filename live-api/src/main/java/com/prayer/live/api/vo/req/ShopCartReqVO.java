package com.prayer.live.api.vo.req;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 13:43
 **/
public class ShopCartReqVO {
	private Long skuId;
	private Integer roomId;
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
