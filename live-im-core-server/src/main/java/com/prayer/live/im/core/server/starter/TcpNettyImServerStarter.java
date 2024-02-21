package com.prayer.live.im.core.server.starter;

import com.prayer.live.im.core.server.common.ChannelHandlerContextCache;
import com.prayer.live.im.core.server.common.ImMsgDecoder;
import com.prayer.live.im.core.server.common.ImMsgEncoder;
import com.prayer.live.im.core.server.handler.tcp.TcpImServerCoreHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:49
 **/
@Component
public class TcpNettyImServerStarter implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(TcpNettyImServerStarter.class);

	//指定监听的端口
	@Value("${live.im.tcp.port}")
	private int port;
	@Resource
	private TcpImServerCoreHandler imServerCoreHandler;
	@Resource
	private Environment environment;
	//基于netty去启动一个java进程，绑定监听的端口
	public void startApplication() throws InterruptedException {
		//处理accept事件
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		//处理read&write事件
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		//netty初始化相关的handler
		bootstrap.childHandler(new ChannelInitializer<>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				//打印日志，方便观察
				LOGGER.info("初始化连接渠道TCP");
				//设计消息体
				//增加编解码器
				ch.pipeline().addLast(new ImMsgDecoder());
				ch.pipeline().addLast(new ImMsgEncoder());
				ch.pipeline().addLast(imServerCoreHandler);
			}
		});
		//基于JVM的钩子函数去实现优雅关闭
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}));
		//获取im的服务注册ip和暴露端口
		String registryIp = "127.0.0.1";
		String registryPort = "9092";

		ChannelHandlerContextCache.setServerIpAddress(registryIp + ":" + registryPort);
		ChannelFuture channelFuture = bootstrap.bind(port).sync();
		LOGGER.info("服务启动成功，监听端口为{}", port);
		//这里会阻塞掉主线程，实现服务长期开启的效果
		channelFuture.channel().closeFuture().sync();
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		Thread nettyServerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					startApplication();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		nettyServerThread.setName("prayer-live-im-server-tcp");
		nettyServerThread.start();
		LOGGER.info("tcp server start");
	}
}
