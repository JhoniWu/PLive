package com.prayer.live.api.service;

import com.prayer.live.api.vo.HomePageVO;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:06
 **/
public interface IHomePageService {
	/**
	 * 初始化页面获取信息
	 * @param userId
	 * @return
	 */
	HomePageVO initPage(Long userId);
}
