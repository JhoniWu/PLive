package com.prayer.live.bank.provider.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.prayer.live.bank.interfaces.constants.OrderStatusEnum;
import com.prayer.live.bank.interfaces.constants.PayProductTypeEnum;
import com.prayer.live.bank.interfaces.dto.PayOrderDTO;
import com.prayer.live.bank.interfaces.dto.PayProductDTO;
import com.prayer.live.bank.provider.dao.mapper.PayOrderMapper;
import com.prayer.live.bank.provider.dao.po.PayOrderPO;
import com.prayer.live.bank.provider.dao.po.PayTopicPO;
import com.prayer.live.bank.provider.service.*;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:43
 **/
public class PayOrderServiceImpl implements PayOrderService{
	private static final Logger LOGGER = LoggerFactory.getLogger(PayOrderServiceImpl.class);

	@Resource
	private PayOrderMapper payOrderMapper;
	@Resource
	private PayProductService payProductService;
	@Resource
	private PayTopicService payTopicService;
	@Resource
	private MQProducer mqProducer;
	@Resource
	private CurrencyAccountService currencyAccountService;
	@Resource
	private CurrencyTradeService currencyTradeService;

	@Override
	public PayOrderPO queryByOrderId(String orderId) {
		LambdaQueryWrapper<PayOrderPO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PayOrderPO::getOrderId, orderId);
		queryWrapper.last("limit 1");
		return payOrderMapper.selectOne(queryWrapper);
	}

	@Override
	public String insertOne(PayOrderDTO payOrderPO) {
		String orderId = UUID.randomUUID().toString();
		payOrderPO.setOrderId(orderId);
		payOrderMapper.insert(ConvertBeanUtils.convert(payOrderPO, PayOrderPO.class));
		return orderId;
	}

	@Override
	public boolean updateOrderStatus(Long id, Integer status) {
		PayOrderPO payOrderPO = new PayOrderPO();
		payOrderPO.setId(id);
		payOrderPO.setStatus(status);
		payOrderMapper.updateById(payOrderPO);
		return true;
	}

	@Override
	public boolean updateOrderStatus(String orderId, Integer status) {
		PayOrderPO payOrderPO = new PayOrderPO();
		payOrderPO.setStatus(status);
		LambdaUpdateWrapper<PayOrderPO> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(PayOrderPO::getOrderId, orderId);
		payOrderMapper.update(payOrderPO, updateWrapper);
		return true;
	}

	@Override
	public boolean payNotify(PayOrderDTO payOrderDTO) {
		PayOrderPO payOrderPO = this.queryByOrderId(payOrderDTO.getOrderId());
		if (payOrderPO == null) {
			LOGGER.error("error payOrderPO, payOrderDTO is {}", payOrderDTO);
			return false;
		}
		PayTopicPO payTopicPO = payTopicService.getByCode(payOrderDTO.getBizCode());
		if (payTopicPO == null || StringUtils.isEmpty(payTopicPO.getTopic())) {
			LOGGER.error("error payTopicPO, payOrderDTO is {}", payOrderDTO);
			return false;
		}
		this.payNotifyHandler(payOrderPO);
		Message message = new Message();
		message.setTopic(payTopicPO.getTopic());
		message.setBody(JSON.toJSONBytes(payOrderPO));
		try {
			SendResult send = mqProducer.send(message);
		} catch (MQBrokerException e) {
			throw new RuntimeException(e);
		} catch (RemotingException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		}
		return true;

	}

	private void payNotifyHandler(PayOrderPO payOrderPO) {
		this.updateOrderStatus(payOrderPO.getOrderId(), OrderStatusEnum.PAYED.getCode());
		Integer productId = payOrderPO.getProductId();
		PayProductDTO payProductDTO = payProductService.getByProductId(productId);
		if(payOrderPO != null && PayProductTypeEnum.LIVE_COIN.getCode().equals(payProductDTO.getType())){
			Long userId = payOrderPO.getUserId();
			JSONObject jsonObject = JSON.parseObject(payProductDTO.getExtra());
			Integer num = jsonObject.getInteger("coin");
			currencyAccountService.incr(userId, num);
		}
	}
}
