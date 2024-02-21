package com.prayer.live.api.service.impl;

import com.prayer.live.api.service.ImService;
import com.prayer.live.api.vo.resp.ImConfigVO;
import com.prayer.live.framework.web.starter.context.LiveRequestContext;
import com.prayer.live.im.constants.AppIdEnum;
import com.prayer.live.im.interfaces.ImTokenRPC;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 17:07
 **/
@Service
public class ImServiceImpl implements ImService {
	@DubboReference
	private ImTokenRPC imTokenRPC;
	@Resource
	private DiscoveryClient discoveryClient;

	@Override
	public ImConfigVO getImConfig() {
		ImConfigVO configVO = new ImConfigVO();
		String token = imTokenRPC.createImLoginToken(LiveRequestContext.getUserId(), AppIdEnum.LIVE_BIZ.getCode());
		configVO.setToken(token);
		buildImServerAddress(configVO);
		return configVO;
	}

	private void buildImServerAddress(ImConfigVO configVO) {
		List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("live-im-core-server");
		Collections.shuffle(serviceInstanceList);
		ServiceInstance aimInstance = serviceInstanceList.get(0);
		configVO.setWsImServerAddress(aimInstance.getHost() + ":8086");
		configVO.setTcpImServerAddress(aimInstance.getHost() + ":8085");
	}

	public void test(){
		String str="haha";
		new Thread() {
			@Override
			public void run() {
				System.out.println(str);
			}
		}.start();
	}
}
