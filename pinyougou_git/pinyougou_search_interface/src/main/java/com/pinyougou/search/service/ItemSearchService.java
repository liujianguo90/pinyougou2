package com.pinyougou.search.service;

import java.util.List;

public interface ItemSearchService {
    //定义一个方法将数据导入到eslatic中
    public void importList(List list);
    //定义一个根据id列表删除索引的方法
    public void deleteByGoodsId(Long[] goodsIdList);
}
