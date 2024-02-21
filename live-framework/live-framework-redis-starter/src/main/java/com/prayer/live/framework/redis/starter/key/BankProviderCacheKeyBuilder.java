package com.prayer.live.framework.redis.starter.key;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 13:28
 **/
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class BankProviderCacheKeyBuilder extends RedisKeyBuilder{
	private static final String BALANCE_CACHE = "balance_cache";

	private static final String PAY_PRODUCT_CACHE = "pay_product_cache";

	private static final String PAY_PRODUCT_ITEM_CACHE = "pay_product_item_cache";

	/**
	 * 构建用户余额cache key
	 *
	 * @param userId
	 * @return
	 */
	public String buildUserBalance(Long userId) {
		return super.getPrefix() + BALANCE_CACHE + super.getSplitItem() + userId;
	}

	/**
	 * 按照产品的类型来进行检索
	 *
	 * @param type
	 * @return
	 */
	public String buildPayProductCache(Integer type) {
		return super.getPrefix() + PAY_PRODUCT_CACHE + super.getSplitItem() + type;
	}

	public String buildPayProductItemCache(Integer productId) {
		return super.getPrefix() + PAY_PRODUCT_ITEM_CACHE + super.getSplitItem() + productId;
	}

}
