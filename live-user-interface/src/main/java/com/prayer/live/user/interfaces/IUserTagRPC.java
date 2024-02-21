package com.prayer.live.user.interfaces;

import com.prayer.live.user.constants.UserTagsEnum;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 15:41
 **/
public interface IUserTagRPC {
	boolean setTag(Long userId, UserTagsEnum userTagsEnum);

	boolean cancelTag(Long userId, UserTagsEnum userTagsEnum);

	boolean containTag(Long userId, UserTagsEnum userTagsEnum);
}
