package com.prayer.live.id.generate.interfaces;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-29 14:38
 **/
public interface IdGenerateRpc {
	/**
	 * 获取有序id
	 * @param id
	 * @return
	 */
	Long getSeqId(Integer id);

	/**
	 * 获取无序id
	 * @param id
	 * @return
	 */
	Long getUnSeqId(Integer id);
}
