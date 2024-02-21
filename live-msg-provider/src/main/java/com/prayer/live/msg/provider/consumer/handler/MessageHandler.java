package com.prayer.live.msg.provider.consumer.handler;

import com.prayer.live.im.dto.ImMsgBody;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:46
 **/
public interface MessageHandler {
	//处理Im服务投递的消息内容
	void onMsgReceive(ImMsgBody imMsgBody);
}
