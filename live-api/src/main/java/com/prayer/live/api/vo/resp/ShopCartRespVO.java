package com.prayer.live.api.vo.resp;

import com.prayer.live.gift.interfaces.dto.ShopCartItemRespDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 13:22
 **/
public class ShopCartRespVO {
	private Long userId;
	private Integer roomId;
	private List<ShopCartItemRespDTO> shopCartItemRespDTOList;
	private Integer totalPrice;

	@Override
	public String toString() {
		return "ShopCartRespVO{" +
			"userId=" + userId +
			", roomId=" + roomId +
			", shopCartItemRespDTOList=" + shopCartItemRespDTOList +
			", totalPrice=" + totalPrice +
			'}';
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public List<ShopCartItemRespDTO> getShopCartItemRespDTOList() {
		return shopCartItemRespDTOList;
	}

	public void setShopCartItemRespDTOList(List<ShopCartItemRespDTO> shopCartItemRespDTOList) {
		this.shopCartItemRespDTOList = shopCartItemRespDTOList;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
}
