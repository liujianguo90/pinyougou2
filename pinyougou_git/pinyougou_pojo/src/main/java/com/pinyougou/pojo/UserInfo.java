package com.pinyougou.pojo;

import com.pinyougou.pojo.entity.User;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private User user;

    private TbAddress tbAddress;

    public UserInfo() {

    }

    public UserInfo(User user, TbAddress tbAddress) {
        this.user = user;
        this.tbAddress = tbAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TbAddress getTbAddress() {
        return tbAddress;
    }

    public void setTbAddress(TbAddress tbAddress) {
        this.tbAddress = tbAddress;
    }
}
