package com.prayer.live.framework.web.starter.error;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-20 13:24
 **/
public class ErrorAssert {
	/**
	 *
	 * 参数不为空
	 *
	 * @param obj
	 * @param error
	 */
	public static void isNotNull(Object obj, LiveBaseError error){
		if(obj == null){
			throw new LiveErrorException(error);
		}
	}

	/**
	 * 字符串不为空
	 *
	 * @param str
	 * @param error
	 */
	public static void isNotBlank(String str, LiveBaseError error){
		if(str == null || str.trim().length() == 0){
			throw new LiveErrorException(error);
		}
	}

	/**
	 * flag == true
	 * @param flag
	 * @param error
	 */
	public static void IsTrue(Boolean flag, LiveBaseError error){
		if(!flag){
			throw new LiveErrorException(error);
		}
	}

	public static void IsTrue(Boolean flag, LiveErrorException error){
		if(!flag){
			throw error;
		}
	}
}
