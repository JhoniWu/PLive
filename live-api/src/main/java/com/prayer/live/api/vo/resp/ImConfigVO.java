package com.prayer.live.api.vo.resp;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:55
 **/
public class ImConfigVO {
	private String token;
	private String wsImServerAddress;
	private String tcpImServerAddress;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWsImServerAddress() {
		return wsImServerAddress;
	}

	public void setWsImServerAddress(String wsImServerAddress) {
		this.wsImServerAddress = wsImServerAddress;
	}

	public String getTcpImServerAddress() {
		return tcpImServerAddress;
	}

	public void setTcpImServerAddress(String tcpImServerAddress) {
		this.tcpImServerAddress = tcpImServerAddress;
	}

}
