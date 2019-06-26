package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.sellergoods.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @date: 2019-06-25 下午 03:53
 * @author: Jop
 * @version: 1.0.0
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;


    /**
     * 查询所有的秒杀商品
     *
     * @return
     */
    @Override
    public List<TbSeckillGoods> findSeckillGoods() {
        //创建实例
        Example example = new Example(TbSeckillGoods.class);
        //构造条件
        Example.Criteria criteria = example.createCriteria();
        //当前时
        Date now = new Date();
        //开始时间要小于现在时间
        criteria.andLessThan("startTime", now);
        //结束时间要大于现在时间
        criteria.andGreaterThan("endTime", now);
        //秒杀商品数要大于0
        criteria.andGreaterThan("num", 0);
        //剩余商品数量要大于0
        criteria.andGreaterThan("stockCount", 0);
        return seckillGoodsMapper.selectByExample(example);
    }

    /**
     * 修改审核状态
     * 1 通过
     * 0 未通过
     *
     * @param ids
     * @return
     */
    @Override
    public void seckillPass(Long[] ids) {
        TbSeckillGoods seckillGoods = new TbSeckillGoods();
        //秒杀审核通过的状态是1
        seckillGoods.setStatus("1");
        //数组转换
        List<Object> longs = Arrays.asList(ids);
        //组装条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        //id在这个范围
        criteria.andIn("id", longs);
        seckillGoodsMapper.updateByExampleSelective(seckillGoods, example);
    }

}
