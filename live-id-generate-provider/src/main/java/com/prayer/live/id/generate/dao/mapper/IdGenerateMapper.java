package com.prayer.live.id.generate.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prayer.live.id.generate.dao.po.IdGeneratePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2023-10-29 14:44
 **/
@Mapper
public interface IdGenerateMapper extends BaseMapper<IdGeneratePO> {
	//引入version概念，通过乐观锁进行控制，避免高并发场景下更新步长和初始值出现问题
	@Update("update t_id_generate_config set next_threshold = next_threshold + step," +
		"current_start = current_start + step," +
		"version = version+1 " +
		"where id = #{id} and version = #{version}")
	int updateNewIdCountAndVersion(@Param("id") int id, @Param("version") int version);

	@Select("select * from t_id_generate_config")
	List<IdGeneratePO> selectAll();
}
