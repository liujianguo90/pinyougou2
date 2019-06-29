package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Tborders implements Serializable {
    private TbOrder tbOrder;
    private TbOrderItem tbOrderItem;


    public Tborders() {
    }

    public Tborders(TbOrder tbOrder, TbOrderItem tbOrderItem) {

        this.tbOrder = tbOrder;
        this.tbOrderItem = tbOrderItem;
    }

    public TbOrder getTbOrder() {

        return tbOrder;
    }

    public void setTbOrder(TbOrder tbOrder) {
        this.tbOrder = tbOrder;
    }

    public TbOrderItem getTbOrderItem() {
        return tbOrderItem;
    }

    public void setTbOrderItem(TbOrderItem tbOrderItem) {
        this.tbOrderItem = tbOrderItem;
    }
}
