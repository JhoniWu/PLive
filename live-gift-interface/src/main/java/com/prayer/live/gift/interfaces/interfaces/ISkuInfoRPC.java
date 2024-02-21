package com.prayer.live.gift.interfaces.interfaces;

import com.prayer.live.gift.interfaces.dto.SkuDetailInfoDTO;
import com.prayer.live.gift.interfaces.dto.SkuInfoDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 17:52
 **/
public interface ISkuInfoRPC {
	/**
	 * 根据主播id查询商品信息
	 *
	 * @param anchorId
	 * @return
	 */
	List<SkuInfoDTO> queryByAnchorId(Long anchorId);

	/**
	 * 查询商品详情
	 *
	 * @param skuId
	 * @return
	 */
	SkuDetailInfoDTO queryBySkuId(Long skuId);
}
