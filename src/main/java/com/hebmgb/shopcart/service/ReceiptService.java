package com.hebmgb.shopcart.service;

import com.hebmgb.shopcart.model.CartItem;
import com.hebmgb.shopcart.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ReceiptService {

    public void calculateTotals(Receipt receipt, CartItem item) {
        // Calculate subtotal
        receipt.setSubTotal(receipt.getSubTotal().add(item.getPrice()));

        // Calculate tax total
        BigDecimal itemTax = item.getPrice().multiply(new BigDecimal(8.25/100.00));
        receipt.setTaxTotal(receipt.getTaxTotal().add(itemTax).setScale(2, RoundingMode.HALF_EVEN));

        // Calculate grand total
        receipt.setGrandTotal(receipt.getSubTotal().subtract(receipt.getTaxTotal()));
    }
}
