package com.hebmgb.shopcart.model;

import java.math.BigDecimal;

public class Receipt {
    private BigDecimal grandTotal = BigDecimal.ZERO;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal taxTotal = BigDecimal.ZERO;
    private BigDecimal taxableSubTotal = BigDecimal.ZERO;


    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getTaxableSubTotal() {
        return taxableSubTotal;
    }

    public void setTaxableSubTotal(BigDecimal taxableSubTotal) {
        this.taxableSubTotal = taxableSubTotal;
    }
}
