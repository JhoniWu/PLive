package com.prayer.live.gift.provider.service.bo;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 21:47
 **/
public class DecrStockNumBO {
	private boolean isSuccess;
	private boolean noStock;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public boolean isNoStock() {
		return noStock;
	}

	public void setNoStock(boolean noStock) {
		this.noStock = noStock;
	}
}

