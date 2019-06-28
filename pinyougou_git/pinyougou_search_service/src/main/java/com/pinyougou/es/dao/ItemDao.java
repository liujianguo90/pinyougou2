package com.pinyougou.es.dao;

import entity.EsItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemDao extends ElasticsearchRepository<EsItem,Long> {
   //定义方法名的方法delete+域名+匹配方式
    void deleteByGoodsIdIn(Long[] ids);
}
