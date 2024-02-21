package com.prayer.live.im.router.provider.cluster;

import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 19:05
 **/
public class ImRouterClusterInvoker<T> extends AbstractClusterInvoker {

	public ImRouterClusterInvoker(Directory<T> directory) {
		super(directory);
	}


	@Override
	protected Result doInvoke(Invocation invocation, List list, LoadBalance loadbalance) throws RpcException {
		checkWhetherDestroyed();
		String ip = (String) RpcContext.getContext().get("ip");
		if(StringUtils.isEmpty(ip)){
			throw new RuntimeException("ip is null");
		}
		//获取到指定的rpc服务提供者的所有地址信息
		List<Invoker<T>> invokers = list(invocation);
		Invoker<T> matchInvoker = invokers.stream().filter(invoker -> {
			//拿到我们服务提供者的暴露地址（ip:端口的格式）
			String serverIp = invoker.getUrl().getHost() + ":" + invoker.getUrl().getPort();
			return serverIp.equals(ip);
		}).findFirst().orElse(null);

		if (matchInvoker == null) {
			throw new RuntimeException("ip is invalid");
		}
		return matchInvoker.invoke(invocation);
	}
}
