package com.hebmgb.shopcart.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Receipt {
    private BigDecimal grandTotal = BigDecimal.ZERO;
    private BigDecimal subTotalBeforeDiscounts = BigDecimal.ZERO;
    private BigDecimal taxTotal = BigDecimal.ZERO;
    private BigDecimal subTotalAfterDiscounts = BigDecimal.ZERO;
    private BigDecimal taxableSubTotalAfterDiscounts = BigDecimal.ZERO;
    private BigDecimal discountTotal = BigDecimal.ZERO;


    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getSubTotalBeforeDiscounts() {
        return subTotalBeforeDiscounts;
    }

    public void setSubTotalBeforeDiscounts(BigDecimal subTotalBeforeDiscounts) {
        this.subTotalBeforeDiscounts = subTotalBeforeDiscounts;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getSubTotalAfterDiscounts() {
        return subTotalAfterDiscounts;
    }

    public void setSubTotalAfterDiscounts(BigDecimal subTotalAfterDiscounts) {
        this.subTotalAfterDiscounts = subTotalAfterDiscounts;
    }

    public BigDecimal getTaxableSubTotalAfterDiscounts() {
        return taxableSubTotalAfterDiscounts;
    }

    public void setTaxableSubTotalAfterDiscounts(BigDecimal taxableSubTotalAfterDiscounts) {
        this.taxableSubTotalAfterDiscounts = taxableSubTotalAfterDiscounts;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }
}
