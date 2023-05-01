package com.hebmgb.shopcart.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public List<CartItem> items = new ArrayList<>();
    public List<Coupon> coupons = new ArrayList<>();
}
