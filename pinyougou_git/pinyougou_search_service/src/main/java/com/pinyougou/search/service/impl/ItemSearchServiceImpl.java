package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.es.dao.ItemDao;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.search.service.impl
 * @date 2019-5-31
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
     private ItemDao itemDao;
    /*将商上架的商品保存到es*/
    @Override
    public void importList(List list) {
        itemDao.saveAll(list);
    }
  /*将下架的商品从es中移走*/
    @Override
    public void deleteByGoodsId(Long[] goodsIdList) {
        itemDao.deleteByGoodsIdIn(goodsIdList);
    }
}
