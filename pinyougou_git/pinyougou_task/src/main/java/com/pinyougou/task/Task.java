package com.pinyougou.task;

import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component //注入到bean
public class Task {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Scheduled(cron = "0/15 * * * * ?")
    //定时器秒杀商品增量同步
    public void seckillGoodsTask() {
        //组装条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        //结束时间要大于当前时间
        criteria.andGreaterThan("endTime", new Date());
        //开始时间要小于当前时间
        criteria.andLessThan("startTime", new Date());
        //状态是1
        criteria.andEqualTo("status", "1");
        //剩余库存数要大于0
        criteria.andGreaterThan("stockCount", 0);
        //排除缓存中已经存有的商品信息
        Set keys = redisTemplate.boundHashOps("seckillGood").keys();
        if (keys != null && keys.size() > 0) {
            //set转成List
            List arrayList = new ArrayList(keys);
            criteria.andNotIn("id", arrayList);
        }
        System.out.println(keys);
        //查询
        List<TbSeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);
        if (seckillGoods != null & seckillGoods.size() > 0) {
            for (TbSeckillGoods seckillGood : seckillGoods) {
                //seckillGood   key   getId   value  seckillGood
                System.out.println("秒杀商品加入了缓存，id为：" + seckillGood.getId() + "标题是" + seckillGood.getTitle());
                redisTemplate.boundHashOps("seckillGood").put(seckillGood.getId(), seckillGood);
            }
        } else {
            System.out.println("缓存中没有商品");
        }
    }
}
