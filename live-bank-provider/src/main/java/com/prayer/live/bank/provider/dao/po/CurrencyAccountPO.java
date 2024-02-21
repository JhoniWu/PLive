package com.prayer.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:44
 **/
@TableName("t_live_currency_account")
public class CurrencyAccountPO {
	@TableId(type = IdType.INPUT)
	private Long userId;
	private int currentBalance;
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

	public int getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
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
		return "CurrencyAccountPO{" +
			"userId=" + userId +
			", currentBalance=" + currentBalance +
			", totalCharged=" + totalCharged +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			'}';
	}
}
