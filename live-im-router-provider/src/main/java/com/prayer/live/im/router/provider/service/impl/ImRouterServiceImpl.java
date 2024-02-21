package com.prayer.live.im.router.provider.service.impl;

import com.prayer.live.im.core.server.interfaces.constants.ImCoreServerConstants;
import com.prayer.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import com.prayer.live.im.dto.ImMsgBody;
import com.prayer.live.im.router.provider.service.ImRouterService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 18:58
 **/
@Service
public class ImRouterServiceImpl implements ImRouterService {

	@DubboReference
	private IRouterHandlerRpc routerHandlerRpc;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	Logger LOGGER = LoggerFactory.getLogger(ImRouterServiceImpl.class);
	@Override
	public boolean sendMsg(ImMsgBody imMsgBody) {
		String bindAddress = stringRedisTemplate.opsForValue().
			get(ImCoreServerConstants.IM_BIND_IP_KEY + imMsgBody.getAppId() + ":" + imMsgBody.getUserId());
		if(StringUtils.isEmpty(bindAddress)){
			return false;
		}
		RpcContext.getContext().set("ip", bindAddress);
		routerHandlerRpc.sendMsg(imMsgBody);
		return true;
	}

	@Override
	public void batchSendMsg(List<ImMsgBody> imMsgBodyList) {
		LOGGER.info("router层处理消息...");
		List<Long> userIdList = imMsgBodyList.stream().map(ImMsgBody::getUserId).collect(Collectors.toList());
		//根据userId将不同的ImMsgBody分类存入Map
		Map<Long, ImMsgBody> sendMsgMap = imMsgBodyList.stream().collect(Collectors.toMap(ImMsgBody::getUserId, x->x));
		//保证整个list集合的appId得是同一个
		Integer appId = imMsgBodyList.get(0).getAppId();
		List<String> cacheKeyList = new ArrayList<>();
		userIdList.forEach(id -> {
			String subKey = ImCoreServerConstants.IM_BIND_IP_KEY + appId + ":" + id;
			cacheKeyList.add(subKey);
		});
		//批量取出每个用户绑定的ip地址
		List<String> ipList = stringRedisTemplate.opsForValue().multiGet(cacheKeyList).stream().filter(Objects::nonNull).collect(Collectors.toList());
		Map<String, List<Long>> idMap = new HashMap<>();
		ipList.forEach(ip -> {
			String currentIp = ip.substring(0, ip.indexOf("%"));
			Long userid = Long.valueOf(ip.substring(ip.indexOf("%")+1));
			List<Long> curIdList = idMap.get(currentIp);
			if(curIdList == null){
				curIdList = new ArrayList<>();
			}
			curIdList.add(userid);
			idMap.put(currentIp, curIdList);
		});
		LOGGER.info("用户ip map,{}",idMap);
		for(String curIp : idMap.keySet()){
			RpcContext.getContext().set("ip", curIp);
			List<ImMsgBody> batchSendMsgGroupByIpList = new ArrayList<>();
			List<Long> ipBindUserIdList = idMap.get(curIp);
			for(Long userId : ipBindUserIdList){
				ImMsgBody imMsgBody = sendMsgMap.get(userId);
				batchSendMsgGroupByIpList.add(imMsgBody);
			}
			LOGGER.info("");
			routerHandlerRpc.batchSendMsg(batchSendMsgGroupByIpList);
			RpcContext.removeContext();
		}
	}
}