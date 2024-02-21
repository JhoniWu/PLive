package com.prayer.live.api.vo;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-18 16:55
 **/
public class HomePageVO {
	private boolean loginStatus;
	private long userId;
	private String nickName;
	private String avatar;
	//是否是主播身份
	private boolean showStartLivingBtn;

	public boolean isShowStartLivingBtn() {
		return showStartLivingBtn;
	}

	public void setShowStartLivingBtn(boolean showStartLivingBtn) {
		this.showStartLivingBtn = showStartLivingBtn;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
