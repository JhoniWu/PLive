package com.prayer.live.account.provider.rpcs;

import com.prayer.live.account.interfaces.IAccountTokenRPC;
import com.prayer.live.account.provider.service.AccountTokenService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-10 00:48
 **/
@DubboService
public class AccountTokenRPC implements IAccountTokenRPC {
	@Resource
	private AccountTokenService accountTokenService;

	@Override
	public String createAndSaveLoginToken(Long userId) {
		return accountTokenService.createAndSaveLoginToken(userId);
	}

	@Override
	public Long getUserIdByToken(String tokenKey) {
		return accountTokenService.getUserIdByToken(tokenKey);
	}
}
