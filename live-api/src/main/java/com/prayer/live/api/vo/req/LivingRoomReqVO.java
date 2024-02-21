package com.prayer.live.api.vo.req;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:55
 **/
public class LivingRoomReqVO {
	private Integer type;
	private int page;
	private int pageSize;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "LivingRoomReqVO{" +
			"type=" + type +
			", page=" + page +
			", pageSize=" + pageSize +
			'}';
	}
}
