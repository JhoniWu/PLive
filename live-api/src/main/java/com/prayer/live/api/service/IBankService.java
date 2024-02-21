package com.prayer.live.api.service;

import com.prayer.live.api.vo.req.PayProductReqVO;
import com.prayer.live.api.vo.resp.PayProductRespVO;
import com.prayer.live.api.vo.resp.PayProductVO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 19:43
 **/
public interface IBankService {
	/**
	 * 查询相关的产品列表信息
	 *
	 * @param type
	 * @return
	 */
	PayProductVO products(Integer type);

	/**
	 * 发起支付
	 *
	 * @param payProductReqVO
	 * @return
	 */
	PayProductRespVO payProduct(PayProductReqVO payProductReqVO);
}
