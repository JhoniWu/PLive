package com.prayer.live.user.utils;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 16:00
 **/
public class TagInfoUtils {
	public static boolean isContain(Long tagInfo, Long matchTag){
		return tagInfo != null && matchTag != null && matchTag > 0 && (tagInfo & matchTag) == matchTag;
	}
}
