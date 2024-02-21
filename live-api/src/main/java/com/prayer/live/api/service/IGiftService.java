package com.prayer.live.api.service;

import com.prayer.live.api.vo.req.GiftReqVO;
import com.prayer.live.api.vo.resp.GiftConfigVO;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 00:40
 **/
public interface IGiftService {
	List<GiftConfigVO> listGift();

	boolean send(GiftReqVO giftReqVO);
}
