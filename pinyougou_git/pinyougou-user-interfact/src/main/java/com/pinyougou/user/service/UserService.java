package com.pinyougou.user.service;

import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.Tborders;
import com.pinyougou.pojo.UserInfo;



import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {
    /**
     * 增加
     */
    public void add(UserInfo user);


    List<Tborders> getOrderInfo(String name);

    TbUser getUser(String name);
}
