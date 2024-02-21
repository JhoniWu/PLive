package com.prayer.live.gift.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:55
 **/
@TableName("t_sku_info")
public class SkuInfoPO {
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long skuId;
	private BigDecimal skuPrice;
	private String skuCode;
	private String name;
	private String iconUrl;
	private String originalIconUrl;
	private Integer status;
	private String remark;
	private Date createTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "SkuInfoPO{" +
			"id=" + id +
			", skuId=" + skuId +
			", skuPrice=" + skuPrice +
			", skuCode='" + skuCode + '\'' +
			", name='" + name + '\'' +
			", iconUrl='" + iconUrl + '\'' +
			", originalIconUrl='" + originalIconUrl + '\'' +
			", status=" + status +
			", remark='" + remark + '\'' +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			'}';
	}

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

	public BigDecimal getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(BigDecimal skuPrice) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
