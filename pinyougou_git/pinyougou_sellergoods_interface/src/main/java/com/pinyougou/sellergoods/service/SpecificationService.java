package com.pinyougou.sellergoods.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbSpecification;

import java.util.List;

/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbSpecification> findPage(int pageNum, int pageSize, TbSpecification specification);
	
	
	/**
	 * 增加
	*/
	public void add(TbSpecification specification);

	/**
	 * 批量提交审核
	 * @param ids
	 */
	public void submitCheck(int[] ids);
	/**
	 * 修改
	 */
	public void update(TbSpecification specification);
	

	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
