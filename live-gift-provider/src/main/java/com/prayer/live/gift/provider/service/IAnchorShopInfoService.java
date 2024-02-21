package com.prayer.live.gift.provider.service;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 18:03
 **/
public interface IAnchorShopInfoService {
	List<Long> querySkuIdByAnchorId(Long anchorId);
}
