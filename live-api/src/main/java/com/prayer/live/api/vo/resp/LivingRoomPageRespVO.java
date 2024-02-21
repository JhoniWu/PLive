package com.prayer.live.api.vo.resp;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:55
 **/
public class LivingRoomPageRespVO {
	private List<LivingRoomRespVO> list;
	private boolean hasNext;

	public List<LivingRoomRespVO> getList() {
		return list;
	}

	public void setList(List<LivingRoomRespVO> list) {
		this.list = list;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	@Override
	public String toString() {
		return "LivingRoomPageRespVO{" +
			"list=" + list +
			", hasNext=" + hasNext +
			'}';
	}
}
