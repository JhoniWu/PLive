package com.prayer.live.gift.provider.service;

import com.prayer.live.gift.provider.dao.po.SkuInfoPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 18:04
 **/
public interface ISkuInfoService {

	List<SkuInfoPO> queryBySkuIds(ArrayList<Long> longs);

	SkuInfoPO queryBySkuId(Long skuId);

	SkuInfoPO queryBySkuIdFromCache(Long skuId);

	boolean uploadSkuInfosToCache(Long anchorId);

	boolean removeSkuInfos(Long anchorId);
}
