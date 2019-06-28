package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSeckillOrder;

import java.util.List;

public interface SeckillOrderService {
    /**
     * 运营商查询所有的秒杀订单
     * @return
     */
    public List<TbSeckillOrder> findAllSeckillOrder();
}
