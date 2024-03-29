package com.pinyougou.sellergoods.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbItemCat;


import java.util.List;

/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface ItemCatService {

	/**
	 * 跟据父ID查询商品分类列表
	 * @param parentId
	 * @return
	 */
	public List<TbItemCat> findByParentId(Long parentId);

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbItemCat> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbItemCat> findPage(int pageNum, int pageSize,int id, TbItemCat itemCat);

	/**
	 * 批量提交审核
	 * @param ids
	 * @return
	 */
	public void submitCheck(int[] ids);
	
	
	/**
	 * 增加
	*/
	public void add(TbItemCat itemCat);
	
	
	/**
	 * 修改
	 */
	public void update(TbItemCat itemCat);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItemCat getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
