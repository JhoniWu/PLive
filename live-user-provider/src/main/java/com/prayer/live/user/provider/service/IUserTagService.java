package com.prayer.live.user.provider.service;

import com.prayer.live.user.constants.UserTagsEnum;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 16:03
 **/
public interface IUserTagService {
	boolean setTag(Long userId, UserTagsEnum userTagsEnum);

	boolean cancelTag(Long userId, UserTagsEnum userTagsEnum);

	boolean containTag(Long userId, UserTagsEnum userTagsEnum);
}
