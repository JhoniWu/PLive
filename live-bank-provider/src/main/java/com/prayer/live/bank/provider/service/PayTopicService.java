package com.prayer.live.bank.provider.service;

import com.prayer.live.bank.provider.dao.po.PayTopicPO;

/**
 * @Author Max Wu
 * @Date: Created in 22:08 2023/8/19
 * @Description
 */
public interface PayTopicService {

    /**
     * 根据code查询
     *
     * @param code
     * @return
     */
    PayTopicPO getByCode(Integer code);
}
