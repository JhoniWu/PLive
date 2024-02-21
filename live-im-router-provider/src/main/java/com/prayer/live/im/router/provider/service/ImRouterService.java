package com.prayer.live.im.router.provider.service;

import com.prayer.live.im.dto.ImMsgBody;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:58
 **/
public interface ImRouterService {
	/**
	 * 发送消息
	 *
	 * @param imMsgBody
	 * @return
	 */
	boolean sendMsg(ImMsgBody imMsgBody);

	/**
	 * 群聊场景下，批量发送消息
	 * @param imMsgBodyList
	 */
	void batchSendMsg(List<ImMsgBody> imMsgBodyList);
}
