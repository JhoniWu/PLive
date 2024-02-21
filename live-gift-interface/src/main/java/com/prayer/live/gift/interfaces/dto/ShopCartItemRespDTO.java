package com.prayer.live.gift.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:35
 **/
public class ShopCartItemRespDTO implements Serializable {

	private static final long serialVersionUID = 4158861653925259273L;

	private Integer count;
	private SkuInfoDTO skuInfoDTO;

	public ShopCartItemRespDTO(Integer count, SkuInfoDTO skuInfoDTO) {
		this.count = count;
		this.skuInfoDTO = skuInfoDTO;
	}

	public SkuInfoDTO getSkuInfoDTO() {
		return skuInfoDTO;
	}

	public void setSkuInfoDTO(SkuInfoDTO skuInfoDTO) {
		this.skuInfoDTO = skuInfoDTO;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
