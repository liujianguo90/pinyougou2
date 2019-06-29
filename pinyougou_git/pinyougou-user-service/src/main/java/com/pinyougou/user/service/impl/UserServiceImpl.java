package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbAddressMapper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.*;
import com.pinyougou.pojo.entity.PageResult;
import com.pinyougou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;


    @Autowired
    private TbAddressMapper tbAddressMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public void add(UserInfo user) {


        //找出登录名
        String username = user.getTbAddress().getUserId();

        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();


        Example example1 = new Example(TbAddress.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId", username);

        TbUser tbUser = new TbUser();
        tbUser.setUsername(username);
        TbUser tbUser1 = tbUserMapper.selectOne(tbUser);


        TbAddress tbAddress = new TbAddress();
        tbAddress.setUserId(username);

        List<TbAddress> list = tbAddressMapper.select(tbAddress);
        for (TbAddress tbAddress1 : list) {

            tbAddress1.setProvinceId(user.getTbAddress().getProvinceId());
            tbAddress1.setTownId(user.getTbAddress().getTownId());
            tbAddress1.setCityId(user.getTbAddress().getCityId());
            tbAddressMapper.updateByExample(tbAddress1, example1);

        }


        String headPic = user.getUser().getHeadPic();
        tbUser1.setHeadPic(headPic);
        tbUser1.setNickName(user.getUser().getNickName());
        tbUser1.setProfession(user.getUser().getProfession());
        tbUser1.setSex(user.getUser().getSex());
        tbUser1.setBirthday(user.getUser().getYear());

        criteria.andEqualTo("username", username);
        tbUserMapper.updateByExample(tbUser1, example);


    }

    @Override
    public List<Tborders> getOrderInfo(String name) {

        Example example = new Example(TbOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", name);
        List<TbOrder> tbOrders = tbOrderMapper.selectByExample(example);


        List<Tborders> list = new ArrayList<>();


        for (TbOrder tbOrder : tbOrders) {
            Tborders tborders = new Tborders();
            tborders.setTbOrder(tbOrder);
            //通过tborder查询
            Long orderId = tbOrder.getOrderId();

            TbOrderItem where = new TbOrderItem();
            where.setOrderId(orderId);
            TbOrderItem tbOrderItem = tbOrderItemMapper.selectOne(where);

            tborders.setTbOrderItem(tbOrderItem);

            list.add(tborders);
        }
        return list;
    }

    @Override
    public TbUser getUser(String name) {
        TbUser user = new TbUser();
        user.setUsername(name);
        return tbUserMapper.selectOne(user);
    }


}
