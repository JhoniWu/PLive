package com.prayer.live.id.generate.service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-29 14:39
 **/
public interface IdGenerateService {
	/**
	 * 获取有序id
	 *
	 * @param id
	 * @return
	 */
	Long getSeqId(Integer id);

	/**
	 * 获取无序id
	 *
	 * @param id
	 * @return
	 */
	Long getUnSeqId(Integer id);
}
