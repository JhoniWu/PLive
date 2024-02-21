package com.prayer.live.gift.provider.rpc;

import com.alibaba.fastjson.JSON;
import com.prayer.live.common.interfaces.topic.GiftProviderTopicNames;
import com.prayer.live.common.interfaces.utils.ConvertBeanUtils;
import com.prayer.live.gift.interfaces.constants.SkuOrderInfoEnum;
import com.prayer.live.gift.interfaces.dto.*;
import com.prayer.live.gift.interfaces.interfaces.ISkuOrderInfoRpc;
import com.prayer.live.gift.provider.dao.po.SkuOrderInfoPO;
import com.prayer.live.gift.provider.service.IShopCartService;
import com.prayer.live.gift.provider.service.ISkuInfoService;
import com.prayer.live.gift.provider.service.ISkuOrderInfoService;
import com.prayer.live.gift.provider.service.ISkuStockInfoService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-04 23:28
 **/
@DubboService
public class SkuOrderInfoRpcImpl implements ISkuOrderInfoRpc {

	@Resource
	private ISkuOrderInfoService skuOrderInfoService;
	@Resource
	private IShopCartService shopCartService;
	@Resource
	private ISkuInfoService skuInfoService;
	@Resource
	private ISkuStockInfoService skuStockInfoService;
	@Resource
	MQProducer mqProducer;

	@Override
	public SkuOrderInfoRespDTO queryByUserIdAndRoomId(Long userId, Integer roomId) {
		return skuOrderInfoService.queryByUserIdAndRoomId(userId, roomId);
	}

	@Override
	public boolean insertOne(SkuOrderInfoReqDTO skuOrderInfoReqDTO) {
		return skuOrderInfoService.insertOne(skuOrderInfoReqDTO) != null;
	}

	@Override
	public boolean updateOrderStatus(SkuOrderInfoReqDTO reqDTO) {
		return skuOrderInfoService.updateOrderStatus(reqDTO);
	}

	@Override
	public SkuPrepareOrderInfoDTO prepareOrder(PrepareOrderReqDTO prepareOrderReqDTO) {
		ShopCartReqDTO shopCartReqDTO = ConvertBeanUtils.convert(prepareOrderReqDTO, ShopCartReqDTO.class);
		ShopCartRespDTO shopCartRespDTO = shopCartService.getCarInfo(shopCartReqDTO);
		List<ShopCartItemRespDTO> cartItemList = shopCartRespDTO.getShopCartItemRespDTOList();
		if(CollectionUtils.isEmpty(cartItemList)){
			return null;
		}
		List<Long> skuIdList = cartItemList.stream().map(item -> item.getSkuInfoDTO().getSkuId()).collect(Collectors.toList());
		boolean status = skuStockInfoService.decrStockNumBySkuIdV3(skuIdList,1);
		if(!status) return null;
		skuOrderInfoService.inValidOldOrder(prepareOrderReqDTO.getUserId());

		//插入SkuOrderInfo记录
		SkuOrderInfoReqDTO skuOrderInfoReqDTO = new SkuOrderInfoReqDTO();
		skuOrderInfoReqDTO.setSkuIdList(skuIdList);
		skuOrderInfoReqDTO.setStatus(SkuOrderInfoEnum.PREPARE_PAY.getCode());
		skuOrderInfoReqDTO.setUserId(prepareOrderReqDTO.getUserId());
		skuOrderInfoReqDTO.setRoomId(prepareOrderReqDTO.getRoomId());
		SkuOrderInfoPO skuOrderInfoPO = skuOrderInfoService.insertOne(skuOrderInfoReqDTO);

		//延迟消息发放
		stockRollBackHandler(skuOrderInfoPO.getUserId(), skuOrderInfoPO.getId());

		List<ShopCartItemRespDTO> shopCartItemRespDTOList = shopCartRespDTO.getShopCartItemRespDTOList();
		List<SkuPrepareOrderItemInfoDTO> itemList = new ArrayList<>();
		Integer totalPrice = 0;
		for(ShopCartItemRespDTO shopCartItemRespDTO : shopCartItemRespDTOList){
			SkuPrepareOrderItemInfoDTO orderItemInfoDTO = new SkuPrepareOrderItemInfoDTO();
			orderItemInfoDTO.setSkuInfoDTO(shopCartItemRespDTO.getSkuInfoDTO());
			orderItemInfoDTO.setCount(shopCartItemRespDTO.getCount());
			itemList.add(orderItemInfoDTO);
			totalPrice = totalPrice + shopCartItemRespDTO.getSkuInfoDTO().getSkuPrice();
		}
		SkuPrepareOrderInfoDTO skuPrepareOrderInfoDTO = new SkuPrepareOrderInfoDTO();
		skuPrepareOrderInfoDTO.setSkuPrepareOrderItemInfoDTOS(itemList);
		skuPrepareOrderInfoDTO.setTotalPrice(totalPrice);
		return skuPrepareOrderInfoDTO;
	}

	private void stockRollBackHandler(Long userId, Long id) {
		RollBackStockDTO rollBackStockDTO = new RollBackStockDTO();
		rollBackStockDTO.setUserId(userId);
		rollBackStockDTO.setOrderId(id);
		Message message = new Message();
		message.setTopic(GiftProviderTopicNames.ROLL_BACK_STOCK);
		message.setBody(JSON.toJSONBytes(rollBackStockDTO));
		//messageDelayLevel=1s 5s 10s(3) 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
		message.setDelayTimeLevel(14);
		try {
			mqProducer.send(message);
		} catch (MQBrokerException e) {
			throw new RuntimeException(e);
		} catch (RemotingException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean payNow(PayNowReqDTO payNowReqDTO) {
		//TODO
		return true;
	}
}
