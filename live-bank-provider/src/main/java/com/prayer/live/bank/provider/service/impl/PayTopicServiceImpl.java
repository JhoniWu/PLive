package com.prayer.live.bank.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.bank.provider.dao.mapper.PayTopicMapper;
import com.prayer.live.bank.provider.dao.po.PayTopicPO;
import com.prayer.live.bank.provider.service.PayTopicService;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import jakarta.annotation.Resource;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:44
 **/
public class PayTopicServiceImpl implements PayTopicService {

	@Resource
	private PayTopicMapper payTopicMapper;

	@Override
	public PayTopicPO getByCode(Integer code) {
		LambdaQueryWrapper<PayTopicPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PayTopicPO::getBizCode, code);
		queryWrapper.eq(PayTopicPO::getStatus, CommonStatusEnum.VALID_STATUS);
		queryWrapper.last("limit 1");
		return payTopicMapper.selectOne(queryWrapper);
	}
}
