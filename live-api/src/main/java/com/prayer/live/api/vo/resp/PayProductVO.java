package com.prayer.live.api.vo.resp;

import java.util.List;
public class PayProductVO {

    /**
     * 当前余额
     */
    private Integer currentBalance;

    /**
     * 一系列的付费产品
     */
    private List<PayProductItemVO> payProductItemVOList;

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<PayProductItemVO> getPayProductItemVOList() {
        return payProductItemVOList;
    }

    public void setPayProductItemVOList(List<PayProductItemVO> payProductItemVOList) {
        this.payProductItemVOList = payProductItemVOList;
    }
}
