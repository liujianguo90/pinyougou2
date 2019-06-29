package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 提交审核
	 * @param ids
	 */
	@Override
	public void submitCheck(int[] ids) {
		for (int i=0; i<ids.length; i++){
			TbItemCat itemCat = new TbItemCat();
			long id =  ids[i];
			itemCat.setId(id);
			itemCat.setStatus("1");
			itemCatMapper.updateByPrimaryKeySelective(itemCat);
		}
	}

    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
		//组装查询条件
		TbItemCat where = new TbItemCat();
		where.setParentId(parentId);
		//查询数据
		List<TbItemCat> catList = itemCatMapper.select(where);

		//把所有商品分类查询出来
		List<TbItemCat> all = this.findAll();
		for (TbItemCat itemCat : all) {
			//Map<分类名称,模板id)
			//把所有的商品分类放入redis缓存中
			redisTemplate.boundHashOps("itemCats").put(itemCat.getName(), itemCat.getTypeId());
		}
		return catList;
    }

    /**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.select(null);
	}


	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize,int id, TbItemCat itemCat) {
		PageResult<TbItemCat> result = new PageResult<TbItemCat>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId",id);

		if(itemCat!=null){			
						//如果字段不为空
			if (itemCat.getName()!=null && itemCat.getName().length()>0) {
				criteria.andLike("name", "%" + itemCat.getName() + "%");
			}
		}


        //查询数据
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbItemCat> info = new PageInfo<TbItemCat>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCat.setStatus("0");
		itemCatMapper.insertSelective(itemCat);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKeySelective(itemCat);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat getById(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        itemCatMapper.deleteByExample(example);
	}
	
	
}
