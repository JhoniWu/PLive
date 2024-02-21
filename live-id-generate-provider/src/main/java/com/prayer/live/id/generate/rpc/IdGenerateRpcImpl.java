package com.prayer.live.id.generate.rpc;

import com.prayer.live.id.generate.interfaces.IdGenerateRpc;
import com.prayer.live.id.generate.service.IdGenerateService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-05 16:01
 **/
@DubboService
public class IdGenerateRpcImpl implements IdGenerateRpc {
	@Resource
	private IdGenerateService idGenerateService;
	@Override
	public Long getSeqId(Integer id) {
		return idGenerateService.getSeqId(id);
	}
	@Override
	public Long getUnSeqId(Integer id) {
		return idGenerateService.getUnSeqId(id);
	}
}