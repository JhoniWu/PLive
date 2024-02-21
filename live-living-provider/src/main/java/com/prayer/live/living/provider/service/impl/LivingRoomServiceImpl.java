package com.prayer.live.living.provider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.common.interfaces.dto.PageWrapper;
import com.prayer.live.common.interfaces.enums.CommonStatusEnum;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.LivingProviderCacheKeyBuilder;
import com.prayer.live.gift.interfaces.interfaces.ISkuOrderInfoRpc;
import com.prayer.live.im.constants.AppIdEnum;
import com.prayer.live.im.core.server.interfaces.dto.ImOffLineDTO;
import com.prayer.live.im.core.server.interfaces.dto.ImOnlineDTO;
import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.im.router.interfaces.rpc.ImRouterRpc;
import com.prayer.live.living.interfaces.dto.LivingPkRespDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomReqDTO;
import com.prayer.live.living.interfaces.dto.LivingRoomRespDTO;
import com.prayer.live.living.provider.dao.mapper.LivingRoomMapper;
import com.prayer.live.living.provider.dao.mapper.LivingRoomRecordMapper;
import com.prayer.live.living.provider.dao.po.LivingRoomPO;
import com.prayer.live.living.provider.dao.po.LivingRoomRecordPO;
import com.prayer.live.living.provider.service.LivingRoomService;
import com.prayer.live.msg.enums.ImMsgBizCodeEnum;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:56
 **/
@Service
public class LivingRoomServiceImpl implements LivingRoomService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LivingRoomRecordPO.class);
	@Resource
	private LivingRoomMapper livingRoomMapper;
	@Resource
	private LivingRoomRecordMapper livingRoomRecordMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private LivingProviderCacheKeyBuilder cacheKeyBuilder;
	@DubboReference
	private ImRouterRpc imRouterRpc;
	@DubboReference
	private ISkuOrderInfoRpc skuOrderInfoRpc;

	@Override
	public List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO) {
		Integer roomId = livingRoomReqDTO.getRoomId();
		Integer appId = livingRoomReqDTO.getAppId();
		String cacheKey = cacheKeyBuilder.buildLivingRoomUserSet(roomId, appId);
		Cursor<Object> cursor = redisTemplate.opsForSet().scan(cacheKey, ScanOptions.scanOptions().match("*").count(100).build());
		List<Long> userIdList = new ArrayList<>();
		while(cursor.hasNext()){
			Integer userId = (Integer) cursor.next();
			userIdList.add(Long.valueOf(userId));
		}
		return userIdList;
	}

	@Override
	public void userOffLineHandler(ImOffLineDTO imOffLineDTO) {
		LOGGER.info("offline handler, imOffLineDTO is {}", imOffLineDTO);
		Integer roomId = imOffLineDTO.getRoomId();
		Integer appId = imOffLineDTO.getAppId();
		Long userId = imOffLineDTO.getUserId();
		String cacheKey = cacheKeyBuilder.buildLivingRoomUserSet(roomId, appId);
		redisTemplate.opsForSet().remove(cacheKey, userId);
	}
	@Override
	public void userOnLineHandler(ImOnlineDTO imOnlineDTO) {
		LOGGER.info("online handler, imOnlineDTO is {}", imOnlineDTO);
		Integer roomId = imOnlineDTO.getRoomId();
		Integer appId = imOnlineDTO.getAppId();
		Long userId = imOnlineDTO.getUserId();
		String cacheKey = cacheKeyBuilder.buildLivingRoomUserSet(roomId, appId);
		redisTemplate.opsForSet().add(cacheKey, userId);
		//当无人访问该直播间后，12小时后会过期
		redisTemplate.expire(cacheKey, 12, TimeUnit.HOURS);
	}

	@Override
	public List<LivingRoomRespDTO> listAllLivingRoomFromDB(Integer type) {
		LambdaQueryWrapper<LivingRoomPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(LivingRoomPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		queryWrapper.eq(LivingRoomPO::getType, type);
		//按照时间倒序展示
		queryWrapper.orderByDesc(LivingRoomPO::getId);
		queryWrapper.last("limit 1000");
		return ConvertBeanUtils.convertList(livingRoomMapper.selectList(queryWrapper), LivingRoomRespDTO.class);
	}

	@Override
	public PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO) {
		String cacheKey = cacheKeyBuilder.buildLivingRoomList(livingRoomReqDTO.getType());
		int page = livingRoomReqDTO.getPage();
		int pageSize = livingRoomReqDTO.getPageSize();
		long total = redisTemplate.opsForList().size(cacheKey);
		List<Object> resultlist = redisTemplate.opsForList().range(cacheKey, (long) (page - 1) *pageSize, ((long) page *pageSize));
		PageWrapper<LivingRoomRespDTO> pageWrapper = new PageWrapper<>();
		if(CollectionUtils.isEmpty(resultlist)){
			pageWrapper.setList(Collections.emptyList());
			pageWrapper.setHasNext(false);
			return pageWrapper;
		} else {
			List<LivingRoomRespDTO> res = ConvertBeanUtils.convertList(resultlist, LivingRoomRespDTO.class);
			pageWrapper.setList(res);
			pageWrapper.setHasNext((long) page *pageSize < total);
			return pageWrapper;
		}
	}
	@Override
	public LivingRoomRespDTO queryByRoomId(Integer roomId) {
		String cacheKey = cacheKeyBuilder.buildLivingRoomObj(roomId);
		LivingRoomRespDTO result = (LivingRoomRespDTO) redisTemplate.opsForValue().get(cacheKey);
		if(result != null) {
			if(result.getId()!=null) {
				return null;
			}
			return result;
		}
		LambdaQueryWrapper<LivingRoomPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(LivingRoomPO::getId, roomId);
		queryWrapper.eq(LivingRoomPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		queryWrapper.last("limit 1");

		LivingRoomRespDTO res = ConvertBeanUtils.convert(livingRoomMapper.selectList(queryWrapper), LivingRoomRespDTO.class);
		if(res==null){
			redisTemplate.opsForValue().set(cacheKey, new LivingRoomRespDTO(), 1, TimeUnit.SECONDS);
			return null;
		}
		redisTemplate.opsForValue().set(cacheKey, res, 30, TimeUnit.MINUTES);
		return res;
	}

	@Override
	public Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO) {
		LivingRoomPO roomPO = ConvertBeanUtils.convert(livingRoomReqDTO, LivingRoomPO.class);
		roomPO.setStatus(CommonStatusEnum.VALID_STATUS.getCode());
		roomPO.setStartTime(new Date());
		livingRoomMapper.insert(roomPO);
		//TODO
		Long anchorId = livingRoomReqDTO.getAnchorId();

		skuOrderInfoRpc.uploadSkuInfo(anchorId);
		String cacheKey = cacheKeyBuilder.buildLivingRoomObj(roomPO.getId());
		redisTemplate.delete(cacheKey);
		return roomPO.getId();
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO) {
		LivingRoomRespDTO roomRespDTO = this.queryByRoomId(livingRoomReqDTO.getRoomId());
		if(roomRespDTO == null){
			return false;
		}
		if(!livingRoomReqDTO.getAnchorId().equals(roomRespDTO.getAnchorId())){
			return false;
		}
		LivingRoomRecordPO newRecordPo = ConvertBeanUtils.convert(livingRoomReqDTO, LivingRoomRecordPO.class);
		newRecordPo.setEndTime(new Date());
		newRecordPo.setStatus(CommonStatusEnum.INVALID_STATUS.getCode());
		livingRoomRecordMapper.insert(newRecordPo);
		livingRoomMapper.deleteById(newRecordPo.getId());
		String cacheKey = cacheKeyBuilder.buildLivingRoomObj(livingRoomReqDTO.getRoomId());
		redisTemplate.delete(cacheKey);
		return true;
	}

	@Override
	public Long queryOnlinePkUserId(Integer roomId) {
		String cacheKey = cacheKeyBuilder.buildLivingOnlinePk(roomId);
		Object userId = redisTemplate.opsForValue().get(cacheKey);
		return userId == null ? null : Long.valueOf((int) userId);
	}

	@Override
	public LivingPkRespDTO onlinePk(LivingRoomReqDTO livingRoomReqDTO) {
		LivingRoomRespDTO curLivingRoom = this.queryByRoomId(livingRoomReqDTO.getRoomId());
		LivingPkRespDTO respDTO = new LivingPkRespDTO();
		respDTO.setOnlineStatus(false);
		if(curLivingRoom.getAnchorId().equals(livingRoomReqDTO.getPkObjId())){
			respDTO.setMsg("该主播不参加PK");
			return respDTO;
		}
		String cacheKey = cacheKeyBuilder.buildLivingOnlinePk(livingRoomReqDTO.getRoomId());
		boolean tryOnline = redisTemplate.opsForValue().setIfAbsent(cacheKey, livingRoomReqDTO.getPkObjId(), 30, TimeUnit.HOURS);
		if(tryOnline){
			List<Long> userIdList = this.queryUserIdByRoomId(livingRoomReqDTO);
			JSONObject json  = new JSONObject();
			json.put("pkObjId", livingRoomReqDTO.getPkObjId());
			json.put("pkObjAvater", "www.picture.xxx");
			batchSendImMsg(userIdList, ImMsgBizCodeEnum.LIVING_ROOM_PK_ONLINE.getCode(), json);
			respDTO.setMsg("连线成功");
			respDTO.setOnlineStatus(true);
		} else {
			respDTO.setMsg("已在连线中");
		}
		return respDTO;
	}

	private void batchSendImMsg(List<Long> userIdList, int code, JSONObject json) {
		List<ImMsgBody> imMsgBodyList = userIdList.stream().map(userId -> {
			ImMsgBody imMsgBody = new ImMsgBody();
			imMsgBody.setAppId(AppIdEnum.LIVE_BIZ.getCode());
			imMsgBody.setBizCode(code);
			imMsgBody.setUserId(userId);
			imMsgBody.setData(json.toJSONString());
			return imMsgBody;
		}).collect(Collectors.toList());
		imRouterRpc.batchSendMsg(imMsgBodyList);
	}

	@Override
	public boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO) {
		String cacheKey = cacheKeyBuilder.buildLivingOnlinePk(livingRoomReqDTO.getRoomId());
		return redisTemplate.delete(cacheKey);
	}

	@Override
	public boolean uploadAllSkuInfos(LivingRoomReqDTO reqDTO) {
		Long anchorId = reqDTO.getAnchorId();
		skuOrderInfoRpc.uploadSkuInfo(anchorId);
		return true;
	}
}