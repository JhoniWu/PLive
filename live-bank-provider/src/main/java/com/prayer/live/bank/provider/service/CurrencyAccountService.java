package com.prayer.live.bank.provider.service;

import com.prayer.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.prayer.live.bank.interfaces.dto.AccountTradeRespDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:37
 **/
public interface CurrencyAccountService {
	boolean insertOne(long userId);
	void incr(long userId, int num);
	void decr(long userId, int num);
	Integer getBalance(long userId);
	AccountTradeRespDTO consumeForSendGift(AccountTradeReqDTO accountTradeReqDTO);
	AccountTradeRespDTO consume(AccountTradeReqDTO accountTradeReqDTO);
}
