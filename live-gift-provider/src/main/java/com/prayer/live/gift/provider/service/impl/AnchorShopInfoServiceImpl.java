package com.prayer.live.gift.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.gift.provider.dao.mapper.AnchorShopInfoMapper;
import com.prayer.live.gift.provider.dao.po.AnchorShopInfoPO;
import com.prayer.live.gift.provider.service.IAnchorShopInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 18:13
 **/
@Service
public class AnchorShopInfoServiceImpl implements IAnchorShopInfoService {

	@Resource
	private AnchorShopInfoMapper anchorShopInfoMapper;

	@Override
	public List<Long> querySkuIdByAnchorId(Long anchorId) {
		LambdaQueryWrapper<AnchorShopInfoPO> qw = new LambdaQueryWrapper<>();
		qw.eq(AnchorShopInfoPO::getAnchorId, anchorId);
		qw.eq(AnchorShopInfoPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		return anchorShopInfoMapper.selectList(qw).stream().map(AnchorShopInfoPO::getSkuId).collect(Collectors.toList());
	}
}
