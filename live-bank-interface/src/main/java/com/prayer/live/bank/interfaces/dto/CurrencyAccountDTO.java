package com.prayer.live.bank.interfaces.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:29
 **/
public class CurrencyAccountDTO implements Serializable {
	private static final long serialVersionUID = -5361051836186504342L;

	private Long userId;
	private int currencyBalance;
	private int totalCharged;
	private Integer status;
	private Date createTime;
	private Date updateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getCurrencyBalance() {
		return currencyBalance;
	}

	public void setCurrencyBalance(int currencyBalance) {
		this.currencyBalance = currencyBalance;
	}

	public int getTotalCharged() {
		return totalCharged;
	}

	public void setTotalCharged(int totalCharged) {
		this.totalCharged = totalCharged;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "CurrencyAccountDTO{" +
			"userId=" + userId +
			", currencyBalance=" + currencyBalance +
			", totalCharged=" + totalCharged +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			'}';
	}
}
