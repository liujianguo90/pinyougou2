package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSeckillGoods;

import java.util.List;

public interface SeckillGoodsService {
    /**
     * 查询所有的秒杀商品
     *
     * @return
     */
    public List<TbSeckillGoods> findSeckillGoods();

    /**
     * 根据后台传过来的秒杀ids进行审核
     * @param ids
     */
    public void seckillPass(Long[] ids);

}
