package com.prayer.live.bank.provider.rpc;

import com.prayer.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.prayer.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.prayer.live.bank.interfaces.rpc.ICurrencyAccountRpc;
import com.prayer.live.bank.provider.service.CurrencyAccountService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:38
 **/
@DubboService
public class CurrencyAccountRpcImpl implements ICurrencyAccountRpc {

	@Resource
	CurrencyAccountService accountService;

	@Override
	public void incr(long userId, int num) {
		accountService.incr(userId, num);
	}

	@Override
	public void decr(long userId, int num) {
		accountService.decr(userId, num);
	}

	@Override
	public Integer getBalance(long userId) {
		return accountService.getBalance(userId);
	}

	@Override
	public AccountTradeRespDTO consumeForSendGift(AccountTradeReqDTO accountTradeReqDTO) {
		return accountService.consumeForSendGift(accountTradeReqDTO);
	}
}
