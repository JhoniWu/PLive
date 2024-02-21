package com.prayer.live.bank.provider.rpc;

import com.prayer.live.bank.interfaces.dto.PayProductDTO;
import com.prayer.live.bank.interfaces.rpc.IPayProductRpc;
import com.prayer.live.bank.provider.service.PayProductService;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:42
 **/
public class PayProductRpcImpl implements IPayProductRpc {

	@Resource
	private PayProductService payProductService;

	@Override
	public List<PayProductDTO> products(Integer type) {
		return payProductService.products(type);
	}

	@Override
	public PayProductDTO getByProductId(Integer productId) {
		return payProductService.getByProductId(productId);
	}
}
