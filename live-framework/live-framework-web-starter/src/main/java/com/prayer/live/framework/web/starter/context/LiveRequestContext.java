package com.prayer.live.framework.web.starter.context;

import com.prayer.live.framework.web.starter.constants.RequestConstants;

import java.util.HashMap;
import java.util.Map;



/**
 * @program: prayer-live
 * @description: 下游用户请求的上下文
 * @author: Max Wu
 * @create: 2023-11-23 21:05
 **/
public class LiveRequestContext {
	private static final ThreadLocal<Map<Object, Object>> resources = new InheritableThreadLocalMap<>();

	public static void set(Object key, Object value){
		if(key == null) throw new IllegalArgumentException("key can not null");
		if(value == null) resources.get().remove(key);
		resources.get().put(key, value);
	}

	public static Long getUserId(){
		Object userid = get(RequestConstants.PRAYER_LIVE_ID);
		return userid==null ? null : (Long) userid;
	}

	private static Object get(Object key) {
		if(key == null) throw new IllegalArgumentException("key can not be null");
		return resources.get().get(key);
	}

	public static void clear(){
		resources.remove();
	}

	//实现父子间进程的本地变量传递
	private static final class InheritableThreadLocalMap<T extends Map<Object, Object>>
		extends InheritableThreadLocal<Map<Object, Object>>{
		@Override
		protected Map<Object, Object> initialValue(){
			return new HashMap<>();
		}

		@Override
		protected Map<Object, Object> childValue(Map<Object, Object> parentValue){
			if(parentValue!=null) return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
			else return null;
		}
	}

}
