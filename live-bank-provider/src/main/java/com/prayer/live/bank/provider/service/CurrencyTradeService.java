package com.prayer.live.bank.provider.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:37
 **/
public interface CurrencyTradeService {

	@Transactional
	boolean insertOne(long userId, int num, int type);
}
