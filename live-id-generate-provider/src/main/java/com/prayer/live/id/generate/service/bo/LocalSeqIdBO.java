package com.prayer.live.id.generate.service.bo;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-29 14:50
 **/
public class LocalSeqIdBO {
	//user id
	private int id;
	//current num
	private AtomicLong currentNum;
	//init num
	private Long currentStart;
	//next num
	private Long nextThreshold;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AtomicLong getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(AtomicLong currentNum) {
		this.currentNum = currentNum;
	}

	public Long getCurrentStart() {
		return currentStart;
	}

	public void setCurrentStart(Long currentStart) {
		this.currentStart = currentStart;
	}

	public Long getNextThreshold() {
		return nextThreshold;
	}

	public void setNextThreshold(Long nextThreshold) {
		this.nextThreshold = nextThreshold;
	}
}
