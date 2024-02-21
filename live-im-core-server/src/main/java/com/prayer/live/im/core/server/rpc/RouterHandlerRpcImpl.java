package com.prayer.live.im.core.server.rpc;

import com.prayer.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import com.prayer.live.im.core.server.service.RouterHandlerService;
import com.prayer.live.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 18:05
 **/
@DubboService
public class RouterHandlerRpcImpl implements IRouterHandlerRpc {
	@Resource
	RouterHandlerService handlerService;

	/**
	 * 转发消息到对应的im服务器上
	 * @param imMsgBody
	 */
	@Override
	public void sendMsg(ImMsgBody imMsgBody) {
		handlerService.onReceive(imMsgBody);
	}

	@Override
	public void batchSendMsg(List<ImMsgBody> batchSendMsgGroupByIpList) {
		batchSendMsgGroupByIpList.forEach(msg -> {
			handlerService.onReceive(msg);
		});
	}
}