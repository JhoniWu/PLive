package com.prayer.live.gift.interfaces.dto;

import java.io.Serializable;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-28 20:43
 **/
public class SkuDetailInfoDTO implements Serializable {
	private static final long serialVersionUID = -5779106022212158314L;

	private Long id;
	private Long skuId;
	private Integer skuPrice;
	private String skuCode;
	private String name;
	private String iconUrl;
	private String originalIconUrl;
	private Integer status;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(Integer skuPrice) {
		this.skuPrice = skuPrice;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getOriginalIconUrl() {
		return originalIconUrl;
	}

	public void setOriginalIconUrl(String originalIconUrl) {
		this.originalIconUrl = originalIconUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
