package com.hebmgb.shopcart.model;

import com.hebmgb.shopcart.exception.ApiRequestException;

import java.math.BigDecimal;

public class Coupon {
    private String couponName;
    private Integer appliedSku;
    private BigDecimal discountPrice;

    public Coupon(String couponName, Integer appliedSku, BigDecimal discountPrice) throws ApiRequestException {
        if (couponName == null || couponName.isEmpty()) {
            throw new ApiRequestException("couponName field was not specified for one or more coupons in the cart");
        } else if (null == discountPrice) {
            throw new ApiRequestException("discountPrice field was not specified for coupon '" + couponName + "'");
        } else if (discountPrice.signum() == -1) {
            throw new ApiRequestException("discountPrice value is negative for item '" + discountPrice + "'");
        }

        this.couponName = couponName;
        this.appliedSku = appliedSku;
        this.discountPrice = discountPrice;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getAppliedSku() {
        return appliedSku;
    }

    public void setAppliedSku(Integer appliedSku) {
        this.appliedSku = appliedSku;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }
}
