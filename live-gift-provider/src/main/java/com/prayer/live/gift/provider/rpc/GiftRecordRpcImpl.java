package com.prayer.live.gift.provider.rpc;

import com.prayer.live.gift.interfaces.dto.GiftRecordDTO;
import com.prayer.live.gift.interfaces.interfaces.IGiftRecordRpc;
import com.prayer.live.gift.provider.service.IGiftRecordService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:29
 **/
@DubboService
public class GiftRecordRpcImpl implements IGiftRecordRpc {
	@Resource
	private IGiftRecordService giftRecordService;
	@Override
	public void insertOne(GiftRecordDTO giftRecordDTO) {
		giftRecordService.insertOne(giftRecordDTO);
	}
}
