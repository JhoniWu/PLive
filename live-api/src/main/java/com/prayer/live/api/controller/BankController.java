package com.prayer.live.api.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.prayer.live.api.service.IBankService;
import com.prayer.live.api.vo.req.PayProductReqVO;
import com.prayer.live.common.interfaces.vo.WebResponseVO;
import com.prayer.live.framework.web.starter.error.BizBaseErrorEnum;
import com.prayer.live.framework.web.starter.error.ErrorAssert;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 19:42
 **/
@RestController
@RequestMapping("/bank")
public class BankController {
	@Resource
	private IBankService bankService;

	@SentinelResource(value = "bank-getProducts", blockHandler = "getForNull")
	@PostMapping("/products")
	public WebResponseVO products(Integer type){
		ErrorAssert.isNotNull(type, BizBaseErrorEnum.PARAM_ERROR);
		return WebResponseVO.success();
	}
	@SentinelResource(value = "getForNull")
	public WebResponseVO getForNull(Integer type, BlockException exception){
		return WebResponseVO.success();
	}

	@PostMapping("/payProduct")
	public WebResponseVO payProduct(PayProductReqVO payProductReqVO) {
		return WebResponseVO.success(bankService.payProduct(payProductReqVO));
	}
}
