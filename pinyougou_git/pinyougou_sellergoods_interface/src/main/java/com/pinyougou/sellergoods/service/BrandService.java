package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;

import java.util.List;

/**
 * 品牌信息业务逻辑接口
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.sellergoods.service
 * @date 2019-5-21
 */
public interface BrandService {
    /**
     * 查询所有品牌
     */
    public List<TbBrand> findAll();


    public void test();
}
