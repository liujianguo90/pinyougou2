package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.sellergoods.service.SeckillOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {
    @Reference
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/findAllSeckillOrder")
    public List<TbSeckillOrder> findAllSeckillOrder() {
        return seckillOrderService.findAllSeckillOrder();
    }
}
