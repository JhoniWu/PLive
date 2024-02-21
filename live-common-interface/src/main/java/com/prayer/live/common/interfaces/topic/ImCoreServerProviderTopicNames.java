package com.prayer.live.common.interfaces.topic;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 17:09
 **/
public class ImCoreServerProviderTopicNames {
	//接收im系统发送的业务消息
	public static final String LIVE_IM_BIZ_MSG_TOPIC = "prayer_live_im_biz_msg_topic";

	//im消息ack
	public static final String LIVE_IM_ACK_MSG_TOPIC = "prayer_live_im_ack_msg_topic";

	//登录IM消息
	public static final String LIVE_ONLINE_TOPIC = "prayer_im_online_topic";

	//退出Im消息
	public static final String LIVE_OFFLINE_TOPIC = "prayer_im_offline_topic";
}
