package com.prayer.live.im.router.interfaces.rpc;

import com.prayer.live.im.dto.ImMsgBody;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:52
 **/
public interface ImRouterRpc {
	boolean sendMsg(ImMsgBody imMsgBody);

	void batchSendMsg(List<ImMsgBody>imMsgBodyList);
}
