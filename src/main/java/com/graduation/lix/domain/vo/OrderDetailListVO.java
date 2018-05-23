package com.graduation.lix.domain.vo;

/**
 * Created by hehe on 18-4-22.
 */
public class OrderDetailListVO {

    private String totalFare;

    private String totalPrice;

    private OrderDetailVO[] orderDetailVOS;

    public OrderDetailVO[] getOrderDetailVOS() {
        return orderDetailVOS;
    }

    public void setOrderDetailVOS(OrderDetailVO[] orderDetailVOS) {
        this.orderDetailVOS = orderDetailVOS;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }
}
