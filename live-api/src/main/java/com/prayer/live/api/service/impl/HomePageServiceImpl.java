package com.prayer.live.api.service.impl;

import com.prayer.live.api.service.IHomePageService;
import com.prayer.live.api.vo.HomePageVO;
import com.prayer.live.user.constants.UserTagsEnum;
import com.prayer.live.user.dto.UserDTO;
import com.prayer.live.user.interfaces.IUserRpc;
import com.prayer.live.user.interfaces.IUserTagRPC;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:07
 **/
@Service
public class HomePageServiceImpl implements IHomePageService {
	@DubboReference
	private IUserRpc userRpc;
	@DubboReference
	private IUserTagRPC userTagRPC;
	@Override
	public HomePageVO initPage(Long userId) {
		UserDTO user = userRpc.getByUserId(userId);
		HomePageVO homePageVO = new HomePageVO();
		if(user!=null){
			homePageVO.setUserId(user.getUserId());
			homePageVO.setAvatar(user.getAvatar());
			homePageVO.setNickName(user.getNickName());
			homePageVO.setShowStartLivingBtn(userTagRPC.containTag(userId, UserTagsEnum.IS_VIP));
		}
		return homePageVO;
	}
}
