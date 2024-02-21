package com.prayer.live.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import com.prayer.live.user.constants.CacheAsyncDeleteCode;
import com.prayer.live.user.constants.UserProviderTopicNames;
import com.prayer.live.user.constants.UserTagFieldNameConstants;
import com.prayer.live.user.constants.UserTagsEnum;
import com.prayer.live.user.dto.UserCacheAsyncDeleteDTO;
import com.prayer.live.user.dto.UserTagDTO;
import com.prayer.live.user.provider.dao.mapper.IUserTagMapper;
import com.prayer.live.user.provider.dao.po.UserTagPO;
import com.prayer.live.user.provider.service.IUserTagService;
import com.prayer.live.user.utils.TagInfoUtils;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 16:16
 **/
@Service
public class UserTagServiceImpl implements IUserTagService {
	@Resource
	private IUserTagMapper userTagMapper;
	@Resource
	private RedisTemplate<String, UserTagDTO> redisTemplate;
	@Resource
	private UserProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private MQProducer mqProducer;


	@Override
	public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
		boolean updateStatus = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
		if(updateStatus) {
			deleteUserTagDTOFromRedis(userId);
			return true;
		}

		String setNxKey = cacheKeyBuilder.buildTagLockKey(userId);
		String setNxResult = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer keySerializer = redisTemplate.getKeySerializer();
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				return (String) connection.execute("set", keySerializer.serialize(setNxKey),
					valueSerializer.serialize("-1"),
					"NX".getBytes(StandardCharsets.UTF_8),
					"EX".getBytes(StandardCharsets.UTF_8),
					"3".getBytes(StandardCharsets.UTF_8));
			}
		});

		if(!"OK".equals(setNxResult)){
			return false;
		}

		UserTagPO userTagPO = userTagMapper.selectById(userId);
		if(userTagPO!=null){
			return false;
		}
		userTagPO = new UserTagPO();
		userTagPO.setUserId(userId);
		userTagMapper.insert(userTagPO);
		updateStatus = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
		redisTemplate.delete(setNxKey);
		return updateStatus;
	}

	/**
	 * 从redis中删除用户标签对象
	 * @param userId
	 */
	private void deleteUserTagDTOFromRedis(Long userId) {
		String redisKey = cacheKeyBuilder.buildTagKey(userId);
		redisTemplate.delete(redisKey);

		UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = new UserCacheAsyncDeleteDTO();
		userCacheAsyncDeleteDTO.setCode(CacheAsyncDeleteCode.USER_TAG_DELETE.getCode());
		Map<String, Object> jsonParam = new HashMap<>();
		jsonParam.put("userId", userId);
		userCacheAsyncDeleteDTO.setJson(JSON.toJSONString(jsonParam));
		Message message = new Message();
		message.setTopic(UserProviderTopicNames.CACHE_ASYNC_DELETE_TOPIC);
		message.setBody(JSON.toJSONString(userCacheAsyncDeleteDTO).getBytes());
		//延迟一秒进行缓存双删
		message.setDelayTimeLevel(1);
		try {
			mqProducer.send(message);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
		boolean cancelStatus = userTagMapper.cancelTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag())>0;
		if(!cancelStatus){
			return false;
		}
		deleteUserTagDTOFromRedis(userId);
		return true;
	}

	@Override
	public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
		UserTagDTO userTagDTO = this.queryByUserIdFromRedis(userId);
		if(userTagDTO == null){
			return false;
		}

		String queryFieldName = userTagsEnum.getFieldName();
		if(UserTagFieldNameConstants.TAG_INFO_01.equals(queryFieldName)){
			return TagInfoUtils.isContain(userTagDTO.getTagInfo01(), userTagsEnum.getTag());
		} else if(UserTagFieldNameConstants.TAG_INFO_02.equals(queryFieldName)){
			return TagInfoUtils.isContain(userTagDTO.getTagInfo02(), userTagsEnum.getTag());
		} else if(UserTagFieldNameConstants.TAG_INFO_03.equals(queryFieldName)){
			return TagInfoUtils.isContain(userTagDTO.getTagInfo03(), userTagsEnum.getTag());
		}
		return false;
	}

	private UserTagDTO  queryByUserIdFromRedis(Long userId) {
		String redisKey = cacheKeyBuilder.buildTagKey(userId);
		UserTagDTO userTagDTO = redisTemplate.opsForValue().get(redisKey);
		if (userTagDTO != null) {
			return userTagDTO;
		}
		UserTagPO userTagPO = userTagMapper.selectById(userId);
		if (userTagPO == null) {
			return null;
		}
		userTagDTO = ConvertBeanUtils.convert(userTagPO, UserTagDTO.class);
		redisTemplate.opsForValue().set(redisKey, userTagDTO);
		redisTemplate.expire(redisKey,30, TimeUnit.MINUTES);
		return userTagDTO;
	}
}
