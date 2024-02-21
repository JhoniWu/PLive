package com.prayer.live.bank.provider.service.impl;

import com.prayer.live.bank.provider.dao.mapper.CurrencyTradeMapper;
import com.prayer.live.bank.provider.dao.po.CurrencyTradePO;
import com.prayer.live.bank.provider.service.CurrencyTradeService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:42
 **/
@Service
public class CurrencyTradeServiceImpl implements CurrencyTradeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyTradeServiceImpl.class);
	@Resource
	private CurrencyTradeMapper currencyTradeMapper;
	@Override
	public boolean insertOne(long userId, int num, int type) {
		CurrencyTradePO tradePO = new CurrencyTradePO();
		tradePO.setUserId(userId);
		tradePO.setNum(num);
		tradePO.setType(type);
		currencyTradeMapper.insert(tradePO);
		LOGGER.info("insert one currency record");
		return true;
	}
}
