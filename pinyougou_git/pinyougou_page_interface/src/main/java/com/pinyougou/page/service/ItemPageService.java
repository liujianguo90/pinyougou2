package com.pinyougou.page.service;

public interface ItemPageService {
/**
 * 这个方法是根据goodsId生成静态页
 *
 * @param goodsId 商品的Id
 * @Return boolean 返回结果成功与否
 * */
public Boolean genItemHtml(Long goodsId);
/**删除静态页的接口
 * @param goodsId
 * @Return boolean
 * */

public Boolean deleteHtmlPage(Long goodsId);
}
