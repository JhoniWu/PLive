package com.prayer.live.gift.interfaces.interfaces;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-19 16:12
 **/
public interface ISkuStockInfoRpc {
	/**
	 * 扣减库存V1
	 * @param skuId
	 * @param num
	 * @return
	 */
	boolean decrStockNumBySkuId(Long skuId, Integer num);

	/**
	 * 扣减库存V2
	 * @param skuId
	 * @param num
	 * @return
	 */
	boolean decrStockNumBySkuIdV2(Long skuId, Integer num);

	/**
	 * 从mysql中预热库存
	 * @param anchorId
	 * @return
	 */
	boolean prepareStockInfoFromMysql(Long anchorId);

	Integer queryStockNum(Long skuId);

	//设计一个接口用于同步redis值到mysql中（定时任务执行，本地定时任务去完成同步行为）
	/**
	 * 同步库存数据到MySQL
	 * @param anchorId
	 */
	boolean syncStockNumToMySQL(Long anchorId);
}
