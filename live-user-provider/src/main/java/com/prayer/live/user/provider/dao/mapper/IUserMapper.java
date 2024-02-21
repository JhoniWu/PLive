package com.prayer.live.user.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prayer.live.user.provider.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-24 22:36
 **/
@Mapper
public interface IUserMapper extends BaseMapper<UserPO> {
}
