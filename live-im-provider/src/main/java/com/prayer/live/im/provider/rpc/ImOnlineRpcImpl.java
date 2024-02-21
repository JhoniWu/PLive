package com.prayer.live.im.provider.rpc;

import com.prayer.live.im.interfaces.ImOnlineRpc;
import com.prayer.live.im.provider.service.ImOnlineService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 20:57
 **/
@DubboService
public class ImOnlineRpcImpl implements ImOnlineRpc {
	@Resource
	ImOnlineService imOnlineService;

	@Override
	public boolean isOnline(long userId, int appId) {
		return imOnlineService.isOnline(userId, appId);
	}
}
