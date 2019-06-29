package com.pinyougou.check.service;
import java.util.List;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import entity.PageResult;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface GoodsService {
	//获取已经通过审核的商品
	public List<TbItem> findItemListByGoodsIdsAndStatus(Long[] goodsIds, String status );

	public void updateStatus(Long[] ids,String status);
	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbGoods> findPage(int pageNum, int pageSize, TbGoods goods);
	
	
	/**
	 * 增加
	*/
	public void add(TbGoods goods);
	
	
	/**
	 * 修改
	 */
	public void update(TbGoods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoods getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
