package com.hebmgb.shopcart.model;

import com.hebmgb.shopcart.exception.ApiRequestException;

import java.math.BigDecimal;

public class CartItem {
    private String itemName;
    private Integer sku;
    private boolean isTaxable;
    private boolean ownBrand;
    private BigDecimal price;

    public CartItem(String itemName, Integer sku, boolean isTaxable, boolean ownBrand, BigDecimal price) throws ApiRequestException {
        if (itemName == null || itemName.isEmpty()) {
            throw new ApiRequestException("ItemName field was not specified for one or more items in the cart");
        } else if (null == price) {
            throw new ApiRequestException("Price field was not specified for item '" + itemName + "'");
        } else if (price.signum() == -1) {
            throw new ApiRequestException("Price value is negative for item '" + itemName + "'");
        }

        this.itemName = itemName;
        this.sku = sku;
        this.isTaxable = isTaxable;
        this.ownBrand = ownBrand;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public Boolean getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(Boolean taxable) {
        isTaxable = taxable;
    }

    public Boolean getOwnBrand() {
        return ownBrand;
    }

    public void setOwnBrand(Boolean ownBrand) {
        this.ownBrand = ownBrand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
