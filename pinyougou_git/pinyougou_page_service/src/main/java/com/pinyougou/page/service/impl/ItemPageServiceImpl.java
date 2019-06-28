package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {
    //注入模板对象
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    //注入查询商品的信息
    @Autowired
    private TbGoodsMapper goodsMapper;
    //注入要查询的商品的描述信息
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    //注入属性文件的属性
    @Value("${pagedir}")
    private String pagedir;
    //将分类的对象注入到系统
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public Boolean genItemHtml(Long goodsId) {
        try {
            //读取模板对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //构建数据模型对象
            Map dataModel = new HashMap();
            //查询商品的基本信息信息
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            //将商品的基本信息加入数模型
            dataModel.put("goods",goods);
            //查询商品描述信息
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            //将查询的商品的描述信息加入数据模型
            dataModel.put("goodsDesc",goodsDesc);
            //通过goodsDesc中的三级分类的Id分别将对应的分类的名字查询出来
            String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            //查询商品的二级分类名字
            String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            //查询三级分类的名字
            String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            //将查询到的分类存入到对应的数据模型中
            dataModel.put("itemCat1",itemCat1);
            dataModel.put("itemCat2",itemCat2);
            dataModel.put("itemCat3",itemCat3);
           //构建条件查询商品的sku的信息
            Example example = new Example(TbItem.class);
            Example.Criteria criteria = example.createCriteria();
            //指定商品的id
            criteria.andEqualTo("goodsId",goodsId);
            //查询的商品都要是经过审核过的商品
            criteria.andEqualTo("status","1");
            //按照默认降序排列
            example.setOrderByClause("isDefault desc");
            //执行查询
            List<TbItem> itemList = itemMapper.selectByExample(example);
            //将查询到的数据塞到模型中
            dataModel.put("itemList",itemList);
            //输出静态HTMl
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pagedir + goodsId + ".html"),"UTF-8"));
            template.process(dataModel,out);
            //关闭流
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**定义一个方法实现静态页的删除
     * @Param GoodsId*/
    public Boolean deleteHtmlPage(Long goodsId){
        try {
            boolean result = new File(pagedir + goodsId + ".html").delete();
            System.out.println("删除商品 "+ goodsId + " 静态页：" + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
         return false;
    }
}
