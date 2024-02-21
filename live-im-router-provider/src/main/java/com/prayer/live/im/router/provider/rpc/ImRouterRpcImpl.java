package com.prayer.live.im.router.provider.rpc;

import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.im.router.interfaces.rpc.ImRouterRpc;
import com.prayer.live.im.router.provider.service.ImRouterService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:54
 **/
@DubboService
public class ImRouterRpcImpl implements ImRouterRpc {
	@Resource
	ImRouterService routerService;

	@Override
	public boolean sendMsg(ImMsgBody imMsgBody) {
		return routerService.sendMsg(imMsgBody);
	}

	@Override
	public void batchSendMsg(List<ImMsgBody> imMsgBodyList) {
		routerService.batchSendMsg(imMsgBodyList);
	}
}
