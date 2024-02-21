package com.prayer.live.gift.provider.service.impl;

import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.gift.interfaces.dto.GiftRecordDTO;
import com.prayer.live.gift.provider.dao.mapper.GiftRecordMapper;
import com.prayer.live.gift.provider.dao.po.GiftRecordPO;
import com.prayer.live.gift.provider.service.IGiftRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:28
 **/
@Service
public class GiftRecordServiceImpl implements IGiftRecordService {
	@Resource
	private GiftRecordMapper giftRecordMapper;
	@Override
	public void insertOne(GiftRecordDTO giftRecordDTO) {
		giftRecordMapper.insert(ConvertBeanUtils.convert(giftRecordDTO, GiftRecordPO.class));
	}
}
