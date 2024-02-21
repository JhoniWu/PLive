package com.prayer.live.im.core.server.service;

import com.prayer.live.im.dto.ImMsgBody;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-15 18:50
 **/
public interface IMsgAckCheckService {
	/**
	 * 客户端发送ack包给服务端后，调用进行ack记录的移除
	 *
	 * @param imMsgBody
	 */
	void doMsgAck(ImMsgBody imMsgBody);

	/**
	 * 记录消息的ack和times
	 *
	 * @param imMsgBody
	 * @param times
	 */
	void recordMsgAck(ImMsgBody imMsgBody, int times);

	/**
	 * 发送延迟消息，用于进行消息重试功能
	 *
	 * @param imMsgBody
	 */
	void sendDelayMsg(ImMsgBody imMsgBody);

	/**
	 * 获取ack消息的重试次数
	 *
	 * @param msgId
	 * @param userId
	 * @param appId
	 * @return
	 */
	int getMsgAckTimes(String msgId, long userId, int appId);

}
