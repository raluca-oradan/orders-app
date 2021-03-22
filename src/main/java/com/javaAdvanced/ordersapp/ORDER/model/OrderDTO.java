package com.javaAdvanced.ordersapp.ORDER.model;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderDTO {
    private int orderNo;
    private String orderDescription;
    private String info;

    @Autowired
    public OrderDTO(int orderNo, String orderDescription, String info) {
        this.orderNo          = orderNo;
        this.orderDescription = orderDescription;
        this.info             = info;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderNo=" + orderNo +
                ", orderDescription='" + orderDescription + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
