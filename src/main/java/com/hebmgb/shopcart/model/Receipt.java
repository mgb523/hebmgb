package com.hebmgb.shopcart.model;

import java.math.BigDecimal;

public class Receipt {
    private BigDecimal grandTotal = BigDecimal.ZERO;

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }
}
