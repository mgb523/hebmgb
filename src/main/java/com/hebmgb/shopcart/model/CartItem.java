package com.hebmgb.shopcart.model;

import java.math.BigDecimal;

public class CartItem {
    private String itemName;
    private Integer sku;
    private Boolean isTaxable;
    private Boolean ownBrand;
    private BigDecimal price;

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
