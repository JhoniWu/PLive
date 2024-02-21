package com.prayer.live.gift.provider.service.bo;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:42
 **/
public class GiftCacheRemoveBO {
	private boolean removeListCache;
	private int giftId;

	public boolean isRemoveListCache() {
		return removeListCache;
	}

	public void setRemoveListCache(boolean removeListCache) {
		this.removeListCache = removeListCache;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	@Override
	public String toString() {
		return "GiftCacheRemoveBO{" +
			"removeListCache=" + removeListCache +
			", giftId=" + giftId +
			'}';
	}
}
