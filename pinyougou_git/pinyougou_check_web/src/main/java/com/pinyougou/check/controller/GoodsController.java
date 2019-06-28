package com.pinyougou.check.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougou.check.mq.MessageSender;
import com.pinyougou.check.service.GoodsService;
import com.pinyougou.pojo.TbItem;
import entity.EsItem;
import entity.MessageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import entity.PageResult;
import entity.Result;
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
	@Autowired
	private MessageSender messageSender;

	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status){
		try {
			goodsService.updateStatus(ids,status);
			//实现商品的上架生成静态页面
			if("1".equals(status)){
				/*for (Long id : ids) {
					itemPageService.genItemHtml(id);
				}*/
				//上架后将审核通过的商品导入es
				//1.1从数据库中获取审核过的商品
				List<TbItem> items = goodsService.findItemListByGoodsIdsAndStatus(ids, status);
				//1.2判断是否有商品已经审核已经上架的
				if(items!=null&& items.size()>0){
					//es实体
					List<EsItem> esItemList = new ArrayList<>();
					EsItem esItem = null;
					for (TbItem item : items) {
						esItem = new EsItem();
						BeanUtils.copyProperties(item,esItem);
						esItem.setPrice(item.getPrice().doubleValue());
						Map specMap = JSON.parseObject(item.getSpec(), Map.class);
						esItem.setSpec(specMap);
						esItemList.add(esItem);
					}
					MessageInfo info = new MessageInfo(
							MessageInfo.METHOD_ADD,  //指定增加索引标识
							esItemList,  //内容
							"shop-check-add",
							"tag-check-add",
							"key-check-add"

					);
					messageSender.sendMessage(info);
					/*//1.2导入索引库
					itemSearchService.importList(esItemList);*/
				 }
			}
			//商品下架实现商品静态页的删除
			if("0".equals(status)){
				/*for (Long id : ids) {
					itemPageService.deleteHtmlPage(id);
				}
				//商品下架后将商品从es删除
				itemSearchService.deleteByGoodsId(ids);*/
				MessageInfo info = new MessageInfo(
						MessageInfo.METHOD_DELETE,  //指定删除索引标识
						ids, //内容
						"shop-check-add",
						"tag-check-add",
						"key-check-add"
				);
				messageSender.sendMessage(info);
			}
			return new Result(true,"修改商品状态成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(false,"修改商品的状态失败");
	}

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int pageNo,int pageSize,@RequestBody TbGoods goods){			
		return goodsService.findPage(pageNo, pageSize,goods);
	}

	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbGoods goods){
		try {
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbGoods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/getById")
	public TbGoods getById(Long id){
		return goodsService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
}
