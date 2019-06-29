package com.pinyougou.sellergoods.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbBrand;


import java.util.List;

/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface BrandService {
	/**
	 * 批量提交审核
	 * @param ids
	 * @return
	 */
	public void submitCheck(int[] ids);

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbBrand> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
<<<<<<< HEAD
    public PageResult<TbBrand> findPage(int pageNum, int pageSize, TbBrand brand);
	
	
	/**
	 * 增加
	*/
	public void add(TbBrand brand);
	
	
	/**
	 * 修改
	 */
	public void update(TbBrand brand);
	
=======
    public List<TbBrand> findAll();
<<<<<<< HEAD
=======
>>>>>>> remotes/kk/master

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbBrand getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

<<<<<<< HEAD
	
=======
    public void test();
>>>>>>> afdb8bc70c0a089d2d025afda5d11c90ab356c0e
>>>>>>> remotes/kk/master
}
