package com.prayer.live.bank.interfaces.rpc;

import com.prayer.live.bank.interfaces.dto.PayOrderDTO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:37
 **/
public interface IPayOrderRpc {
	String insertOne(PayOrderDTO payOrderDTO);
	boolean updateOrderStatus(Long id, Integer status);
	boolean updateOrderStatus(String orderId, Integer status);
	boolean payNotify(PayOrderDTO payOrderDTO);
}
