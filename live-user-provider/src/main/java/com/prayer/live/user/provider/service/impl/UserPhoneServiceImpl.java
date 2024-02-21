package com.prayer.live.user.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.common.interfaces.utils.DESUtils;
import com.prayer.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import com.prayer.live.id.generate.enums.IdTypeEnum;
import com.prayer.live.id.generate.interfaces.IdGenerateRpc;
import com.prayer.live.user.dto.UserDTO;
import com.prayer.live.user.dto.UserLoginDTO;
import com.prayer.live.user.dto.UserPhoneDTO;
import com.prayer.live.user.provider.dao.mapper.IUserPhoneMapper;
import com.prayer.live.user.provider.dao.po.UserPhonePO;
import com.prayer.live.user.provider.service.IUserPhoneService;
import com.prayer.live.user.provider.service.IUserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 17:33
 **/
@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
	Logger logger = LoggerFactory.getLogger(UserPhoneServiceImpl.class);
	@Resource
	private IUserPhoneMapper userPhoneMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private UserProviderCacheKeyBuilder cacheKeyBuilder;
	@Resource
	private IUserService userService;
	@DubboReference
	private IdGenerateRpc idGenerateRpc;

	@Override
	public UserLoginDTO login(String phone) {
		if(StringUtils.isEmpty(phone)){
			return null;
		}
		//是否注册过
		UserPhoneDTO userPhoneDTO = this.queryByPhone(phone);
		logger.info("查询到的userDTO is {}", userPhoneDTO.toString());
		//如果注册过，创建token，返回userId
		if (userPhoneDTO != null) {
			return UserLoginDTO.loginSuccess(userPhoneDTO.getUserId(), createAndSaveLoginToken(userPhoneDTO.getUserId()));
		}
		//如果没注册过，生成user信息，插入手机记录，绑定userId
		return registerAndLogin(phone);
	}

	private UserLoginDTO registerAndLogin(String phone) {
		Long userId = idGenerateRpc.getUnSeqId(IdTypeEnum.USER_ID.getCode());
		UserDTO userDTO = new UserDTO();
		userDTO.setNickName("prayer-live-" + userId);
		userDTO.setUserId(userId);
		userService.insertOne(userDTO);
		UserPhonePO userPhonePO = new UserPhonePO();
		userPhonePO.setUserId(userId);
		userPhonePO.setPhone(DESUtils.encrypt(phone));
		//userPhonePO.setStatus(CommonStatusEum.VALID_STATUS.getCode());
		userPhoneMapper.insert(userPhonePO);
		redisTemplate.delete(cacheKeyBuilder.buildUserPhoneObjKey(phone));
		return UserLoginDTO.loginSuccess(userId, createAndSaveLoginToken(userId));
	}

	private String createAndSaveLoginToken(Long userId) {
		String token = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(cacheKeyBuilder.buildUserLoginTokenKey(token), userId, 30, TimeUnit.DAYS);
		return token;
	}

	@Override
	public UserPhoneDTO queryByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		String redisKey = cacheKeyBuilder.buildUserPhoneObjKey(phone);
		UserPhoneDTO userPhoneDTO = (UserPhoneDTO) redisTemplate.opsForValue().get(redisKey);
		if (userPhoneDTO != null) {
			//属于空值缓存对象
			if (userPhoneDTO.getUserId() == null) {
				return null;
			}
			return userPhoneDTO;
		}

		userPhoneDTO = this.queryByPhoneFromDB(phone);
		if (userPhoneDTO != null) {
			userPhoneDTO.setPhone(DESUtils.decrypt(userPhoneDTO.getPhone()));
			redisTemplate.opsForValue().set(redisKey, userPhoneDTO, 30, TimeUnit.MINUTES);
			return userPhoneDTO;
		}

		//缓存击穿，空值缓存
		userPhoneDTO = new UserPhoneDTO();
		redisTemplate.opsForValue().set(redisKey, userPhoneDTO, 5, TimeUnit.MINUTES);
		return null;
	}

	private UserPhoneDTO queryByPhoneFromDB(String phone) {
		LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserPhonePO::getPhone, DESUtils.encrypt(phone));
		//queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEum.VALID_STATUS.getCode());
		queryWrapper.last("limit 1");
		return ConvertBeanUtils.convert(userPhoneMapper.selectOne(queryWrapper), UserPhoneDTO.class);

	}
	private List<UserPhoneDTO> queryByUserIdFromDB(Long userId) {
		LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserPhonePO::getUserId, userId);
		//queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEum.VALID_STATUS.getCode());
		queryWrapper.last("limit 1");
		return ConvertBeanUtils.convertList(userPhoneMapper.selectList(queryWrapper), UserPhoneDTO.class);
	}

	@Override
	public List<UserPhoneDTO> queryByUserId(Long userId) {
		if (userId == null || userId < 10000) {
			return Collections.emptyList();
		}
		String redisKey = cacheKeyBuilder.buildUserPhoneListKey(userId);
		List<Object> userPhoneList = redisTemplate.opsForList().range(redisKey, 0, -1);
		if (!CollectionUtils.isEmpty(userPhoneList)) {
			//证明是空值缓存
			if (((UserPhoneDTO) userPhoneList.get(0)).getUserId() == null) {
				return Collections.emptyList();
			}
			return userPhoneList.stream().map(x -> (UserPhoneDTO) x).collect(Collectors.toList());
		}

		List<UserPhoneDTO> userPhoneDTOS = this.queryByUserIdFromDB(userId);
		if (!CollectionUtils.isEmpty(userPhoneDTOS)) {
			userPhoneDTOS.stream().forEach(x -> x.setPhone(DESUtils.decrypt(x.getPhone())));
			redisTemplate.opsForList().leftPushAll(redisKey, userPhoneDTOS.toArray());
			redisTemplate.expire(redisKey, 30, TimeUnit.MINUTES);
			return userPhoneDTOS;
		}
		//缓存击穿，空对象缓存
		redisTemplate.opsForList().leftPush(redisKey, new UserPhoneDTO());
		redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);
		return Collections.emptyList();
	}
}
