package com.prayer.live.gift.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-29 18:00
 **/
@TableName("t_category_info")
public class CategoryInfoPO {
	@TableId(type = IdType.AUTO)
	private Integer level;
	private String categoryName;
	private Integer parentId;
	private Integer status;
	private Date createTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "CategoryInfoPO{" +
			"level=" + level +
			", categoryName='" + categoryName + '\'' +
			", parentId=" + parentId +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			'}';
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
