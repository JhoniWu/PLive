package com.prayer.live.gift.provider.rpc;

import com.prayer.live.gift.interfaces.dto.GiftConfigDTO;
import com.prayer.live.gift.interfaces.interfaces.IGiftConfigRpc;
import com.prayer.live.gift.provider.service.IGiftConfigService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-19 23:29
 **/
@DubboService
public class GiftConfigRpcImpl implements IGiftConfigRpc {

	@Resource
	private IGiftConfigService giftConfigService;

	@Override
	public GiftConfigDTO getByGiftId(Integer giftId) {
		return giftConfigService.getByGiftId(giftId);
	}

	@Override
	public List<GiftConfigDTO> queryGiftList() {
		return giftConfigService.queryGiftList();
	}

	@Override
	public void insertOne(GiftConfigDTO giftConfigDTO) {
		giftConfigService.insertOne(giftConfigDTO);
	}

	@Override
	public void updateOne(GiftConfigDTO giftConfigDTO) {
		giftConfigService.updateOne(giftConfigDTO);
	}
}
