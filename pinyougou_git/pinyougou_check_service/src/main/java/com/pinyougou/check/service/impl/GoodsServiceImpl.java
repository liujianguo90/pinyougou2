package com.pinyougou.check.service.impl;
import java.util.Arrays;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.pinyougou.check.service.GoodsService;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	@Autowired
	private TbItemMapper itemMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.select(null);
	}

	@Override
	public List<TbItem> findItemListByGoodsIdsAndStatus(Long[] goodsIds, String status) {
		Example example = new Example(TbItem.class);
		Example.Criteria criteria = example.createCriteria();
		//查询条件设置-spu列表
		List longs = Arrays.asList(goodsIds);
		criteria.andIn("goodsId", longs);
		//查询条件设置-商品状态
		criteria.andEqualTo("status", status);
		//查询结果
		List<TbItem> items = itemMapper.selectByExample(example);
		return items;
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		//要更新的内容
		TbGoods goods = new TbGoods();
		goods.setIsMarketable(status);
		//更新条件组装
		Example example = new Example(TbGoods.class);
		Example.Criteria criteria = example.createCriteria();
		List longs = Arrays.asList(ids);
		criteria.andIn("id",longs);
		//执行更新
		goodsMapper.updateByExampleSelective(goods ,example);
	}
	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbGoods goods) {
		PageResult<TbGoods> result = new PageResult<TbGoods>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(goods!=null){			
						//如果字段不为空
			if (goods.getSellerId()!=null && goods.getSellerId().length()>0) {
				criteria.andLike("sellerId", "%" + goods.getSellerId() + "%");
			}
			//如果字段不为空
			if (goods.getGoodsName()!=null && goods.getGoodsName().length()>0) {
				criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
			}
			//如果字段不为空
			if (goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0) {
				criteria.andLike("auditStatus", "%" + goods.getAuditStatus() + "%");
			}
			//如果字段不为空
			if (goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0) {
				criteria.andLike("isMarketable", "%" + goods.getIsMarketable() + "%");
			}
			//如果字段不为空
			if (goods.getCaption()!=null && goods.getCaption().length()>0) {
				criteria.andLike("caption", "%" + goods.getCaption() + "%");
			}
			//如果字段不为空
			if (goods.getSmallPic()!=null && goods.getSmallPic().length()>0) {
				criteria.andLike("smallPic", "%" + goods.getSmallPic() + "%");
			}
			//如果字段不为空
			if (goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0) {
				criteria.andLike("isEnableSpec", "%" + goods.getIsEnableSpec() + "%");
			}
			//如果字段不为空
			if (goods.getIsDelete()!=null && goods.getIsDelete().length()>0) {
				criteria.andLike("isDelete", "%" + goods.getIsDelete() + "%");
			}
	
		}

        //查询数据
        List<TbGoods> list = goodsMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbGoods> info = new PageInfo<TbGoods>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbGoods goods) {
		goodsMapper.insertSelective(goods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods goods){
		goodsMapper.updateByPrimaryKeySelective(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoods getById(Long id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        goodsMapper.deleteByExample(example);
	}
	
	
}