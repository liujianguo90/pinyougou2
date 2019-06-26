package com.pinyougou.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.sellergoods.service.SeckillGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @date: 2019-06-25 下午 03:54
 * @author: Jop
 * @version: 1.0.0
 */

@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {
    @Reference
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/findAll")
    public List<TbSeckillGoods> findSeckillGoods() {
        List<TbSeckillGoods> seckillGoods = seckillGoodsService.findSeckillGoods();
        return seckillGoods;
    }

    @RequestMapping("/seckillPass")
    public Result seckillPass(Long[] ids){
        try {
            seckillGoodsService.seckillPass(ids);
            return new Result("秒杀审核成功",true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("秒杀审核失败",false);
        }
    }
}
