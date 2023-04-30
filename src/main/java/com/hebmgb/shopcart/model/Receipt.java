package com.hebmgb.shopcart.model;

import java.math.BigDecimal;

public class Receipt {
    private BigDecimal grandTotal = BigDecimal.ZERO;
    private BigDecimal beforeDiscountSubtotal;
    private BigDecimal discountTotal;
    private BigDecimal afterDiscountsSubtotal;
    private BigDecimal afterDiscountTaxableSubtotal;
    private BigDecimal taxTotal;

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getBeforeDiscountSubtotal() {
        return beforeDiscountSubtotal;
    }

    public void setBeforeDiscountSubtotal(BigDecimal beforeDiscountSubtotal) {
        this.beforeDiscountSubtotal = beforeDiscountSubtotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getAfterDiscountsSubtotal() {
        return afterDiscountsSubtotal;
    }

    public void setAfterDiscountsSubtotal(BigDecimal afterDiscountsSubtotal) {
        this.afterDiscountsSubtotal = afterDiscountsSubtotal;
    }

    public BigDecimal getAfterDiscountTaxableSubtotal() {
        return afterDiscountTaxableSubtotal;
    }

    public void setAfterDiscountTaxableSubtotal(BigDecimal afterDiscountTaxableSubtotal) {
        this.afterDiscountTaxableSubtotal = afterDiscountTaxableSubtotal;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }
}
