package com.prayer.live.im.core.server.interfaces.rpc;

import com.prayer.live.im.dto.ImMsgBody;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 17:42
 **/
public interface IRouterHandlerRpc {
	void sendMsg(ImMsgBody imMsgBody);

	void batchSendMsg(List<ImMsgBody> batchSendMsgGroupByIpList);
}
