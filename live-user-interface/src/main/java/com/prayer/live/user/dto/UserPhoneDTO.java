package com.prayer.live.user.dto;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-11-01 17:10
 **/
public class UserPhoneDTO implements Serializable {
	@Serial
	private static final long serialVersionUID = -8176889394601308804L;

	private Long id;
	private Long userId;
	private String phone;
	private Integer status;
	private Date createTime;
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public String toString() {
		return "UserPhoneDTO{" +
			"id=" + id +
			", userId=" + userId +
			", phone='" + phone + '\'' +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			'}';
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
