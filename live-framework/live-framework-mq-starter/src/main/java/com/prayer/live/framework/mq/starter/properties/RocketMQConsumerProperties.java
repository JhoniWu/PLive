package com.prayer.live.framework.mq.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-12 15:45
 **/
@ConfigurationProperties(prefix = "live.rmq.consumer")
@Configuration
public class RocketMQConsumerProperties {
	private String nameSrv;
	private String groupName;

	public String getNameSrv() {
		return nameSrv;
	}

	public void setNameSrv(String nameSrv) {
		this.nameSrv = nameSrv;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "RocketMQConsumerProperties{" +
			"nameSrv='" + nameSrv + '\'' +
			", groupName='" + groupName + '\'' +
			'}';
	}
}
