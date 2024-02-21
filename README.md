# 基于Spring Cloud微服务架构的电商直播消息服务套件

---
## 项目简介
本项目以电商直播带货业务为背景，开发的电商直播消息服务组件，主要实现了直播间即时通讯、商品秒杀、直播送礼、用户中台服务等。

---

## 项目技术栈
本项目基于Springboot和SpringCloud搭建，基于Netty对C/S通讯长连接进行管理，基于Dubbo进行服务间通信，基于Redis，MySQL进行存储，使用RocketMQ作为消息队列，使用MyBatis-plus作为持久层框架。
服务模块划分：
* live-im 即时通讯模块
  * im-core-server 即时通讯接入层
  * im-router 路由转发层
  * im-provider IM下游业务层
  * msg-provider 业务消息处理层
* live-bank 交易模块
* live-framework 框架模块
* live-common 公共组件模块
* live-id-generate 分布式id生成器模块
* live-living 直播服务模块
* live-gift 礼物系统模块
* live-api API层
* live-user 用户中台模块
* live-Client 用户测试模块
* live-account 账户模块