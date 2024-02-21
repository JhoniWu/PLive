package com.prayer.live.im.provider.rpc;

import com.prayer.live.im.interfaces.ImTokenRPC;
import com.prayer.live.im.provider.service.ImTokenService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:51
 **/
@DubboService
public class ImTokenRPCImpl implements ImTokenRPC {
	@Resource
	private ImTokenService tokenService;
	@Override
	public String createImLoginToken(long userId, int appId) {
		return tokenService.createImLoginToken(userId, appId);
	}

	@Override
	public Long getUserIdByToken(String token) {
		return tokenService.getUserIdByToken(token);
	}
}
