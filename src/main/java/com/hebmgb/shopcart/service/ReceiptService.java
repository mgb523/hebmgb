package com.hebmgb.shopcart.service;

import com.hebmgb.shopcart.model.CartItem;
import com.hebmgb.shopcart.model.Coupon;
import com.hebmgb.shopcart.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReceiptService {

    public void calculateTotals(Receipt receipt, CartItem item, List<Coupon> coupons) {
        // subtotalBeforeDiscounts
        receipt.setSubTotalBeforeDiscounts(receipt.getSubTotalBeforeDiscounts().add(item.getPrice()));

        // discountTotal, subTotalAfterDiscounts
        BigDecimal afterDiscountPrice = item.getPrice();
        if (null != coupons && !coupons.isEmpty()) {
            Coupon clippedCoupon = coupons.stream().filter(c -> c.getAppliedSku().equals(item.getSku())).findAny().orElse(null);
            if (null != clippedCoupon) {
                afterDiscountPrice = item.getPrice().subtract(clippedCoupon.getDiscountPrice());
                receipt.setDiscountTotal(receipt.getDiscountTotal().add(clippedCoupon.getDiscountPrice()));

                receipt.setSubTotalAfterDiscounts(receipt.getSubTotalAfterDiscounts().add(afterDiscountPrice));
            } else {
                receipt.setSubTotalAfterDiscounts(receipt.getSubTotalAfterDiscounts().add(item.getPrice()));
            }
        } else {
            receipt.setSubTotalAfterDiscounts(receipt.getSubTotalAfterDiscounts().add(item.getPrice()));
        }

        // taxTotal, taxableSubTotalAfterDiscounts
        BigDecimal afterTaxPrice = afterDiscountPrice;
        if (item.getIsTaxable()) {
            BigDecimal itemTax = afterDiscountPrice.multiply(BigDecimal.valueOf((8.25 / 100.00))).setScale(2, RoundingMode.HALF_EVEN);
            afterTaxPrice = afterTaxPrice.add(itemTax);
            receipt.setTaxTotal(receipt.getTaxTotal().add(itemTax));

            receipt.setTaxableSubTotalAfterDiscounts(receipt.getTaxableSubTotalAfterDiscounts().add(afterDiscountPrice));
        }

        // grandTotal
        receipt.setGrandTotal(receipt.getGrandTotal().add(afterTaxPrice));
    }
}
