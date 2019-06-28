package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbSeckillOrderMapper;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.sellergoods.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Autowired
    private TbSeckillOrderMapper seckillOrderMapper;

    @Override
    public List<TbSeckillOrder> findAllSeckillOrder() {
        return seckillOrderMapper.select(null);
    }
}
