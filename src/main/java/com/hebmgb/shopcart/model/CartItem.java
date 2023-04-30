package com.hebmgb.shopcart.model;

import com.hebmgb.shopcart.exception.ApiRequestException;
import lombok.NonNull;

import java.math.BigDecimal;

public class CartItem {
    private String itemName;
    private Integer sku;
    private Boolean isTaxable;
    private Boolean ownBrand;
    private BigDecimal price;

    public CartItem(String itmeName, Integer sku, Boolean isTaxable, Boolean ownBrand, BigDecimal price) throws ApiRequestException {
        if (null == price) {
            throw new ApiRequestException("Price field was not specified");
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
