package com.prayer.live.im.router.provider.cluster;

import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-16 19:05
 **/
public class ImRouterCluster implements Cluster {
	@Override
	public <T> Invoker<T> join(Directory<T> directory, boolean buildFilterChain) throws RpcException {
		return new ImRouterClusterInvoker<>(directory);
	}
}
