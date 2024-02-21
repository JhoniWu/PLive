package com.prayer.live.gift.interfaces.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:34
 **/
public class ShopCartRespDTO implements Serializable {

	private static final long serialVersionUID = -5469768769765086756L;

	private Long userId;
	private Integer roomId;
	private List<ShopCartItemRespDTO> shopCartItemRespDTOList;



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
}

