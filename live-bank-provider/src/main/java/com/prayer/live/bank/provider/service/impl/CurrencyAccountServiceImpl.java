package com.prayer.live.bank.provider.service.impl;

import com.prayer.live.bank.interfaces.constants.TradeTypeEnum;
import com.prayer.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.prayer.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.prayer.live.bank.provider.dao.mapper.CurrencyAccountMapper;
import com.prayer.live.bank.provider.dao.po.CurrencyAccountPO;
import com.prayer.live.bank.provider.service.CurrencyAccountService;
import com.prayer.live.bank.provider.service.CurrencyTradeService;
import com.prayer.live.framework.redis.starter.key.BankProviderCacheKeyBuilder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:42
 **/
@Service
public class CurrencyAccountServiceImpl implements CurrencyAccountService {
	@Resource
	private CurrencyTradeService currencyTradeService;
	@Resource
	private CurrencyAccountMapper accountMapper;
	@Resource
	private BankProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

	@Override
	public boolean insertOne(long userId) {
		try {
			CurrencyAccountPO accountPO = new CurrencyAccountPO();
			accountPO.setUserId(userId);
			accountMapper.insert(accountPO);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public void incr(long userId, int num) {
		accountMapper.incr(userId, num);
	}

	@Override
	public void decr(long userId, int num) {
		String cacheKey = cacheKeyBuilder.buildUserBalance(userId);

		redisTemplate.opsForValue().decrement(cacheKey);
		threadPoolExecutor.execute(() -> {
			consumeDBHandler(userId, num);
		});
	}
	@Transactional
	public void consumeDBHandler(long userId, int num) {
		accountMapper.decr(userId, num);
		currencyTradeService.insertOne(userId, num * -1, TradeTypeEnum.SEND_GIFT_TRADE.getCode());
	}

	@Override
	public Integer getBalance(long userId) {
		String cacheKey = cacheKeyBuilder.buildUserBalance(userId);
		Object cacheBalance = redisTemplate.opsForValue().get(cacheKey);
		if(cacheBalance != null){
			if((Integer) cacheBalance == -1){
				return null;
			}

			return (Integer) cacheBalance;
		}

		Integer currentBalance = accountMapper.queryBalance(userId);
		if(currentBalance==null) {
			redisTemplate.opsForValue().set(cacheKey, -1, 5, TimeUnit.MINUTES);
		}
		redisTemplate.opsForValue().set(cacheKey, currentBalance, 30, TimeUnit.MINUTES);
		return currentBalance;
	}

	@Override
	public AccountTradeRespDTO consumeForSendGift(AccountTradeReqDTO accountTradeReqDTO) {
		long userId = accountTradeReqDTO.getUserId();
		int num = accountTradeReqDTO.getNum();
		Integer balance = getBalance(userId);
		if(balance == null || balance - num < 0){
			return AccountTradeRespDTO.buildFail(userId, "balance is not enough", 1);
		}
		this.decr(userId, num);
		return AccountTradeRespDTO.buildSuccess(userId, "consume success");
	}

	@Override
	public AccountTradeRespDTO consume(AccountTradeReqDTO accountTradeReqDTO) {
		return AccountTradeRespDTO.buildSuccess(-1L, "扣费成功");
	}
}
