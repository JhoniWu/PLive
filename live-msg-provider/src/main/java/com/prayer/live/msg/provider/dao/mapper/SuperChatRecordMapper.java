package com.prayer.live.msg.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prayer.live.msg.provider.dao.po.SuperChatRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-15 13:20
 **/
@Mapper
public interface SuperChatRecordMapper extends BaseMapper<SuperChatRecordPO> {
	@Select("select * from t_super_chat_record where id > #{id} and room_id = #{roomId}" )
	List<SuperChatRecordPO> queryNewSc(@Param("id") Long id, @Param("room_id") Integer roomId);
}
