package com.prayer.live.msg.provider.service.impl;

import com.prayer.live.framework.redis.starter.key.MsgProviderCacheKeyBuilder;
import com.prayer.live.msg.dto.MsgCheckDTO;
import com.prayer.live.msg.enums.MsgSendResultEnum;
import com.prayer.live.msg.provider.config.ApplicationProperties;
import com.prayer.live.msg.provider.config.ThreadPoolManager;
import com.prayer.live.msg.provider.dao.mapper.SmsMapper;
import com.prayer.live.msg.provider.dao.po.SmsPO;
import com.prayer.live.msg.provider.service.ISmsService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-02 23:49
 **/
@Service
public class SmsServiceImpl implements ISmsService {
	Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private MsgProviderCacheKeyBuilder msgProviderCacheKeyBuilder;
	@Resource
	private SmsMapper smsMapper;
	@Resource
	private ApplicationProperties applicationProperties;


	@Override
	public MsgSendResultEnum sendLoginCode(String phone) {
		logger.info("Sms服务收到手机号，准备生成验证码");
		if (StringUtils.isEmpty(phone)) {
			return MsgSendResultEnum.MSG_PARAM_ERROR;
		}
		//生成验证码，4位，6位（取它），有效期（30s，60s），同一个手机号不能重发，redis去存储验证码
		String codeCacheKey = msgProviderCacheKeyBuilder.buildSmsLoginCodeKey(phone);
		if (redisTemplate.hasKey(codeCacheKey)) {
			logger.warn("该手机号短信发送过于频繁，phone is "+ phone);
			return MsgSendResultEnum.SEND_FAIL;
		}
		int code = RandomUtils.nextInt(1000, 9999);
		redisTemplate.opsForValue().set(codeCacheKey, code, 300, TimeUnit.SECONDS);
		//发送验证码
		logger.info("记录验证码记录");
		ThreadPoolManager.commonAsyncPool.execute(() -> {
			boolean sendStatus = sendSmsToCCP(phone, code);
			if (sendStatus) {
				logger.info("插入验证码记录");
				insertOne(phone, code);
			}
		});
		//插入验证码发送记录
		return MsgSendResultEnum.SEND_SUCCESS;
	}

	private boolean sendSmsToCCP(String phone, int code) {
		return true;
	}

	@Override
	public MsgCheckDTO checkLoginCode(String phone, Integer code) {
		//参数校验
		if (StringUtils.isEmpty(phone) || code == null || code < 1000) {
			return new MsgCheckDTO(false, "参数异常");
		}
		//redis校验验证码
		String codeCacheKey = msgProviderCacheKeyBuilder.buildSmsLoginCodeKey(phone);
		Integer cacheCode = (Integer) redisTemplate.opsForValue().get(codeCacheKey);
		if (cacheCode == null || cacheCode < 1000) {
			return new MsgCheckDTO(false, "验证码已过期");
		}
		if (cacheCode.equals(code)) {
			redisTemplate.delete(codeCacheKey);
			return new MsgCheckDTO(true, "验证码校验成功");
		}
		return new MsgCheckDTO(false, "验证码校验失败");
	}

	@Override
	public void insertOne(String phone, Integer code) {
		logger.info("验证码记录插入数据库");
		SmsPO smsPO = new SmsPO();
		smsPO.setPhone(phone);
		smsPO.setCode(code);
		int insert = smsMapper.insert(smsPO);
		logger.info("插入记录数量{}",insert);
	}
}
