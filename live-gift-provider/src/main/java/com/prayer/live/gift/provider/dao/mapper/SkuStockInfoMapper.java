package com.prayer.live.gift.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prayer.live.gift.provider.dao.po.SkuStockInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author idea
 * @Date: Created in 19:58 2023/10/3
 * @Description
 */
@Mapper
public interface SkuStockInfoMapper extends BaseMapper<SkuStockInfoPO> {
	@Update("update t_sku_stock_info set stock_num=stock_num-#{num}, version = version + 1 where sku_id=#{skuId} and stock_num-#{num}>0 and version=#{version}")
	int decrStockNumBySkuId(@Param("skuId") Long skuId, @Param("num") Integer num, @Param("version")Integer version);
}
