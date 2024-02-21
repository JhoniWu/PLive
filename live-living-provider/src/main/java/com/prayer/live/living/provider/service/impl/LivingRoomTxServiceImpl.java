package com.prayer.live.living.provider.service.impl;

import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.LivingProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.interfaces.ISkuOrderInfoRpc;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;
import com.prayer.live.living.provider.dao.mapper.LivingRoomMapper;
import com.prayer.live.living.provider.dao.mapper.LivingRoomRecordMapper;
import com.prayer.live.living.provider.dao.po.LivingRoomRecordPO;
import com.prayer.live.living.provider.service.LivingRoomService;
import com.prayer.live.living.provider.service.LivingRoomTxService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-26 19:44
 **/
@Service
public class LivingRoomTxServiceImpl implements LivingRoomTxService {

	@Resource
	private LivingRoomService livingRoomService;
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private LivingRoomRecordMapper livingRoomRecordMapper;
	@Resource
	private LivingRoomMapper livingRoomMapper;
	@Resource
	private LivingProviderCacheKeyBuilder cacheKeyBuilder;
	@DubboReference
	private ISkuOrderInfoRpc skuOrderInfoRpc;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO) {
		LivingRoomRespDTO livingRoomRespDTO = livingRoomService.queryByRoomId(livingRoomReqDTO.getRoomId());
		if (livingRoomRespDTO == null) {
			return false;
		}
		if (!(livingRoomRespDTO.getAnchorId().equals(livingRoomReqDTO.getAnchorId()))) {
			return false;
		}
		LivingRoomRecordPO livingRoomRecordPO = ConvertBeanUtils.convert(livingRoomRespDTO, LivingRoomRecordPO.class);
		livingRoomRecordPO.setEndTime(new Date());
		livingRoomRecordPO.setStatus(CommonStatusEnum.INVALID_STATUS.getCode());
		livingRoomRecordMapper.insert(livingRoomRecordPO);
		livingRoomMapper.deleteById(livingRoomRecordPO.getId());
		//移除掉直播间cache
		String cacheKey = cacheKeyBuilder.buildLivingRoomObj(livingRoomReqDTO.getRoomId());
		redisTemplate.delete(cacheKey);
		boolean b = skuOrderInfoRpc.removeLesskey(livingRoomRecordPO.getAnchorId());
		if(b) return true;
		else throw new RuntimeException("删除商品信息失败");
	}
}
