package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
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
public class BrandServiceImpl implements BrandService {
<<<<<<< HEAD

	@Autowired
	private TbBrandMapper brandMapper;

	/**
	 * 提交审核
	 * @param ids
	 */
	@Override
	public void submitCheck(int[] ids) {
		for (int i=0; i<ids.length; i++){
			TbBrand brand = new TbBrand();
			long id =  ids[i];
			brand.setId(id);
			brand.setStatus("1");
			brandMapper.updateByPrimaryKeySelective(brand);
		}
	}

	/**
	 * 查询全部
	 */
	@Override
	public List<TbBrand> findAll() {
		return brandMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbBrand brand) {
		PageResult<TbBrand> result = new PageResult<TbBrand>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(brand!=null){			
						//如果字段不为空
			if (brand.getName()!=null && brand.getName().length()>0) {
				criteria.andLike("name", "%" + brand.getName() + "%");
			}
			//如果字段不为空
			if (brand.getFirstChar()!=null && brand.getFirstChar().length()>0) {
				criteria.andLike("firstChar", "%" + brand.getFirstChar() + "%");
			}
	
		}

        //查询数据
        List<TbBrand> list = brandMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbBrand> info = new PageInfo<TbBrand>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbBrand brand) {
		//设置默认为未审核
		brand.setStatus("0");
		brandMapper.insertSelective(brand);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbBrand brand){
		brandMapper.updateByPrimaryKeySelective(brand);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbBrand getById(Long id){
		return brandMapper.selectByPrimaryKey(id);
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
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);
		criteria.andIn("status",status);

		//跟据查询条件删除数据
        brandMapper.deleteByExample(example);
	}
	
	
=======
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
>>>>>>> remotes/kk/master
}
