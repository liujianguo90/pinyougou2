package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbSpecification;

import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	/**
	 * 提交审核
	 * @param ids
	 */
	@Override
	public void submitCheck(int[] ids) {
		for (int i=0; i<ids.length; i++){
			TbSpecification specification = new TbSpecification();
			long id =  ids[i];
			specification.setId(id);
			specification.setStatus("1");
			specificationMapper.updateByPrimaryKeySelective(specification);
		}
	}
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbSpecification specification) {
		PageResult<TbSpecification> result = new PageResult<TbSpecification>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						//如果字段不为空
			if (specification.getSpecName()!=null && specification.getSpecName().length()>0) {
				criteria.andLike("specName", "%" + specification.getSpecName() + "%");
			}
	
		}

        //查询数据
        List<TbSpecification> list = specificationMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbSpecification> info = new PageInfo<TbSpecification>(list);
        result.setPages(info.getPages());
		
		return result;
	}


	@Override
	public void add(TbSpecification specification) {
		specification.setStatus("0");
		specificationMapper.insertSelective(specification);
	}

	@Override
	public void update(TbSpecification specification) {

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
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);
        criteria.andIn("status",status);

        //跟据查询条件删除数据
        specificationMapper.deleteByExample(example);

//		for (Long id : ids) {
//			TbSpecificationOption where = new TbSpecificationOption();
//			where.setSpecId(id);
//			specificationOptionMapper.delete(where);
//		}
        //删除规格选项列表
        Example exampleSpec = new Example(TbSpecificationOption.class);
        Example.Criteria criteria1 = exampleSpec.createCriteria();
        criteria1.andIn("specId",longs);
        specificationOptionMapper.deleteByExample(exampleSpec);

    }
	
	
}
