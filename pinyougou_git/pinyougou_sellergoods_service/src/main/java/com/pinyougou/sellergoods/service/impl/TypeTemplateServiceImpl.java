package com.pinyougou.sellergoods.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;
	@Autowired
	private TbSpecificationOptionMapper optionMapper;


	/**
	 * 提交审核
	 * @param ids
	 */
	@Override
	public void submitCheck(int[] ids) {
		for (int i=0; i<ids.length; i++){
			TbTypeTemplate template = new TbTypeTemplate();
			long id =  ids[i];
			template.setId(id);
			template.setStatus("1");
			typeTemplateMapper.updateByPrimaryKeySelective(template);
		}
	}

    @Override
    public List<Map> findSpecList(Long id) {
		//查询模板
		TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		//把json串转成List<Map>
		List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);

		TbSpecificationOption where = null;
		//遍历规格列表，查询规格选项列表
		for (Map map : list) {
			//组装查询条件，跟据规格id查询规格选项列表
			where = new TbSpecificationOption();
			where.setSpecId(new Long(map.get("id").toString()));
			List<TbSpecificationOption> options = optionMapper.select(where);
			//保存数据到结果列表中
			map.put("options", options);
		}
		return list;
    }

    /**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbTypeTemplate typeTemplate) {
		PageResult<TbTypeTemplate> result = new PageResult<TbTypeTemplate>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbTypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){			
						//如果字段不为空
			if (typeTemplate.getName()!=null && typeTemplate.getName().length()>0) {
				criteria.andLike("name", "%" + typeTemplate.getName() + "%");
			}
			//如果字段不为空
			if (typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0) {
				criteria.andLike("specIds", "%" + typeTemplate.getSpecIds() + "%");
			}
			//如果字段不为空
			if (typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0) {
				criteria.andLike("brandIds", "%" + typeTemplate.getBrandIds() + "%");
			}
			//如果字段不为空
			if (typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0) {
				criteria.andLike("customAttributeItems", "%" + typeTemplate.getCustomAttributeItems() + "%");
			}
	
		}

        //查询数据
        List<TbTypeTemplate> list = typeTemplateMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbTypeTemplate> info = new PageInfo<TbTypeTemplate>(list);
        result.setPages(info.getPages());
		//更新缓存
		//this.saveToRedis();

		return result;
	}

	@Autowired
	private RedisTemplate redisTemplate;
	/**
	 * 将品牌与规格放入redis缓存中的方法
	 */
	public void saveToRedis(){
		//分别将品牌数据和规格数据放入缓存（Hash）。以模板ID作为key,以品牌列表和规格列表作为值。
		List<TbTypeTemplate> all = this.findAll();
		for (TbTypeTemplate typeTemplate : all) {
			//把品牌json串转换为list<map<对象
			List<Map> brandIds = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
			//Map<模板id,List<Map>>
			redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(), brandIds);

			//把规格json串转换为list<map<对象
			List<Map> specList  = this.findSpecList(typeTemplate.getId());
			//Map<模板id,List<Map>>
			redisTemplate.boundHashOps("specList").put(typeTemplate.getId(), specList);
		}
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		//默认状态为未提交
		typeTemplate.setStatus("0");
		typeTemplateMapper.insertSelective(typeTemplate);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate getById(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
		//只有未申请和已驳回的可以删除
		List status = new ArrayList();
		status.add(0);
		status.add(3);
        //构建查询条件
        Example example = new Example(TbTypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);
		criteria.andIn("status",status);

        //跟据查询条件删除数据
        typeTemplateMapper.deleteByExample(example);
	}
	
	
}
