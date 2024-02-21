package com.prayer.live.id.generate.service.bo;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-29 14:50
 **/
public class LocalUnSeqIdBO {
	private int id;

	private ConcurrentLinkedQueue<Long> idQueue;

	private Long currentStart;

	private Long nextThreshold;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConcurrentLinkedQueue<Long> getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(ConcurrentLinkedQueue<Long> idQueue) {
		this.idQueue = idQueue;
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
