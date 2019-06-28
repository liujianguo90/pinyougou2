package com.pinyougou.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.sellergoods.service.GoodsService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	@Reference
	private GoodsService goodsService;

   //将生成页面的对象注入
	@Reference
	private ItemPageService itemPageService;
	//将service对象注入进来
	@Reference
	private ItemSearchService itemSearchService;
	/**定义一个生成静态页的方法
	 * @Param goodsId
	 * @Return boolean*/
	@RequestMapping("/genHtml")
	public Result genHtml(Long goodsId) {
		try {
			itemPageService.genItemHtml(goodsId);
			return new Result(true,"生成的静态页面成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(false,"生成的静态页面失败");
	}
	/**
	 * 删除静态页的的方法
	 * @Param goodsId
	 * @Return boolean
	 * */
	@RequestMapping("/deleteHtmlPage")
	public Result deleteHtmlPage(Long goodsId){
		try {
			itemPageService.deleteHtmlPage(goodsId);
			return new Result(true,"删除静态页面成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(false,"删除静态页面失败");
	}
	/**
	 * 将上架的商品放在es中
	 * @Raram List 关于审核的商品的集合
	 * @return boolean*/
	@RequestMapping("/importListToEs")
	public Result importList(List list){
		try {
			itemSearchService.importList(list);
			return new Result(true,"将上架的商品转到es正常");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(false,"将上架的商品转到es失败");
	}

	@RequestMapping("/deleteGoodFromEs")
	public Result deleteByGoodsId(Long[] goodsIdList){
		try {
			itemSearchService.deleteByGoodsId(goodsIdList);
			return new Result(true,"将下架的商品删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(false,"将下架的商品删除失败");
	}
	/*----------------------------关于增删改查的基本操作---------------------------------*/
}
