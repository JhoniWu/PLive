package com.prayer.live.bank.interfaces.rpc;

import com.prayer.live.bank.interfaces.dto.PayProductDTO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 18:38
 **/
public interface IPayProductRpc {
	/**
	 * 返回批量的商品信息
	 *
	 * @param type 不同的业务场景所使用的产品
	 */
	List<PayProductDTO> products(Integer type);


	/**
	 * 根据产品id查询
	 *
	 * @param productId
	 * @return
	 */
	PayProductDTO getByProductId(Integer productId);
}
