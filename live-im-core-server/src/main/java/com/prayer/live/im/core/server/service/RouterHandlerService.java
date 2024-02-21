package com.prayer.live.im.core.server.service;

import com.prayer.live.im.dto.ImMsgBody;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 18:06
 **/
public interface RouterHandlerService {
	/**
	 * 收到业务请求时进行处理
	 *
	 * @param imMsgBody
	 */
	void onReceive(ImMsgBody imMsgBody);

	/**
	 * 发送消息给客户端
	 *
	 * @param imMsgBody
	 * @return
	 */
	boolean sendMsgToClient(ImMsgBody imMsgBody);
}
