package com.prayer.live.bank.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prayer.live.bank.provider.dao.po.CurrencyAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-01-21 12:52
 **/
@Mapper
public interface CurrencyAccountMapper extends BaseMapper<CurrencyAccountPO> {
	@Update("update t_live_currency_account set current_balance = current_balance + #{num} where user_id = #{user_id}")
	void incr(@Param("userId") long userId, @Param("num") int num);

	@Select("select current_balance from t_live_currency_account where user_id=#{userId} and status = 1 limit 1")
	Integer queryBalance(@Param("userId") long userId);

	@Update("update t_live_currency_account set current_balance = current_balance - #{num} where user_id = #{userId}")
	void decr(@Param("userId") long userId,@Param("num") int num);
}
