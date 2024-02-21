package com.prayer.live.bank.provider.rpc;

import com.prayer.live.bank.interfaces.dto.PayOrderDTO;
import com.prayer.live.bank.interfaces.rpc.IPayOrderRpc;
import com.prayer.live.bank.provider.service.PayOrderService;
import jakarta.annotation.Resource;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:35
 **/
public class PayOrderRpcImpl implements IPayOrderRpc {

	@Resource
	private PayOrderService payOrderService;

	@Override
	public String insertOne(PayOrderDTO payOrderDTO) {
		return payOrderService.insertOne(payOrderDTO);
	}

	@Override
	public boolean updateOrderStatus(Long id, Integer status) {
		return payOrderService.updateOrderStatus(id, status);
	}

	@Override
	public boolean updateOrderStatus(String orderId, Integer status) {
		return payOrderService.updateOrderStatus(orderId, status);
	}

	@Override
	public boolean payNotify(PayOrderDTO payOrderDTO) {
		return payOrderService.payNotify(payOrderDTO);
	}
}
