package com.prayer.live.api.vo.resp;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-22 13:22
 **/
public class SkuInfoVO {
	private Long skuId;
	private Integer skuPrice;
	private String skuCode;
	private String name;
	private String iconUrl;
	private String originalIconUrl;
	private String remark;

	@Override
	public String toString() {
		return "SkuInfoVO{" +
			"skuId=" + skuId +
			", skuPrice=" + skuPrice +
			", skuCode='" + skuCode + '\'' +
			", name='" + name + '\'' +
			", iconUrl='" + iconUrl + '\'' +
			", originalIconUrl='" + originalIconUrl + '\'' +
			", remark='" + remark + '\'' +
			'}';
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}