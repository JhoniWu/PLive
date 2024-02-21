package com.prayer.live.gift.provider.service;

import com.prayer.live.gift.interfaces.dto.RollBackStockDTO;
import com.prayer.live.gift.provider.dao.po.SkuStockInfoPO;
import com.prayer.live.gift.provider.service.bo.DecrStockNumBO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 21:46
 **/
public interface ISkuStockInfoService {
	boolean updateStockNum(Long skuId, Integer num);

	/**
	 * 根据skuId更新库存
	 * @param skuId
	 * @param num
	 * @return
	 */
	DecrStockNumBO decrStockNumBySkuId(Long skuId, Integer num);

	boolean decrStockNumBySkuIdV2(Long skuId, Integer num);

	SkuStockInfoPO queryBySkuId(Long skuId);

	List<SkuStockInfoPO> queryBySkuIds(List<Long> skuIdList);
	/**
	 * 库存回滚
	 * @param rollBackStockDTO
	 */
	void stockRollBackHandler(RollBackStockDTO rollBackStockDTO);

	boolean decrStockNumBySkuIdV3(List<Long> skuIdList, int num);
}
