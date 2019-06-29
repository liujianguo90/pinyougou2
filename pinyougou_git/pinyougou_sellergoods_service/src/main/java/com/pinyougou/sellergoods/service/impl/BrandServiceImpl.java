package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.sellergoods.service.impl
 * @date 2019-5-21
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.select(null);
    }
<<<<<<< HEAD
=======

    @Override
    public void test() {
        System.out.println("test");
    }
>>>>>>> afdb8bc70c0a089d2d025afda5d11c90ab356c0e
}
