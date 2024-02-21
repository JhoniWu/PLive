package com.prayer.live.gift.provider.rpc;

import com.prayer.live.gift.interfaces.dto.SkuDetailInfoDTO;
import com.prayer.live.gift.interfaces.dto.SkuInfoDTO;
import com.prayer.live.gift.interfaces.interfaces.ISkuInfoRPC;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 16:11
 **/
public class SkuInfoRpcImpl implements ISkuInfoRPC {
	@Override
	public List<SkuInfoDTO> queryByAnchorId(Long anchorId) {
		return null;
	}

	@Override
	public SkuDetailInfoDTO queryBySkuId(Long skuId) {
		return null;
	}
}
