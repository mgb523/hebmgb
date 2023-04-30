package com.hebmgb.shopcart.service;

import com.hebmgb.shopcart.model.CartItem;
import com.hebmgb.shopcart.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ReceiptService {

    public void calculateTotals(Receipt receipt, CartItem item) {
        // subtotal = receipt subtotal + item price
        receipt.setSubTotal(receipt.getSubTotal().add(item.getPrice()));

        // tax total = receipt tax total + item tax
        BigDecimal itemTax = item.getPrice().multiply(new BigDecimal(8.25/100.00));
        receipt.setTaxTotal(receipt.getTaxTotal().add(itemTax).setScale(2, RoundingMode.HALF_EVEN));

        // grand total = subtotal + tax total
        receipt.setGrandTotal(receipt.getSubTotal().add(receipt.getTaxTotal()));
    }
}
