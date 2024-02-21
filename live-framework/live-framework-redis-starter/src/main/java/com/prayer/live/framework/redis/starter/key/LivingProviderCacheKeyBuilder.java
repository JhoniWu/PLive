package com.prayer.live.framework.redis.starter.key;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 23:01
 **/
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class LivingProviderCacheKeyBuilder extends RedisKeyBuilder{
	private static final String LIVING_ROOM_LIST = "living_room_list";
	private static final String LIVING_ROOM_OBJ = "living_room_obj";
	private static final String REFRESH_LIVING_ROOM_LIST_LOCK = "refresh_living_room_list_lock";
	private static final String LIVING_ROOM_USER_LIST = "living_room_user_list";
	private static final String LIVING_ROOM_USER_SET = "living_room_user_set";
	private static final String LIVING_ONLINE_PK = "living_online_pk";
	public String buildLivingRoomUserList(Integer roomId) {
		return super.getPrefix() + LIVING_ROOM_USER_LIST + super.getSplitItem() + roomId;
	}

	public String buildRefreshLivingRoomListLock() {
		return super.getPrefix() + REFRESH_LIVING_ROOM_LIST_LOCK;
	}

	public String buildLivingRoomObj(Integer roomId) {
		return super.getPrefix() + LIVING_ROOM_OBJ + super.getSplitItem() + roomId;
	}

	public String buildLivingRoomList(Integer type) {
		return super.getPrefix() + LIVING_ROOM_LIST + super.getSplitItem() + type;
	}
	public String buildLivingRoomUserSet(Integer roomId, Integer appId) {
		return super.getPrefix() + LIVING_ROOM_USER_SET + super.getSplitItem() + appId + super.getSplitItem() + roomId;
	}
	public String buildLivingOnlinePk(Integer roomId) {
		return super.getPrefix() + LIVING_ONLINE_PK + super.getSplitItem() + roomId;
	}
}
