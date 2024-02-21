package com.prayer.live.common.interfaces.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-17 22:42
 **/
public class PageWrapper<T> implements Serializable {

	private static final long serialVersionUID = 7596546786411428785L;
	private List<T> list;
	private boolean hasNext;

	public List<T> getList(){
		return list;
	}

	public void setList(List<T> list){
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
		return "PageWrapper{" +
			"list=" + list +
			", hasNext=" + hasNext +
			'}';
	}
}

