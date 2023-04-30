package com.hebmgb.shopcart.service;

import com.hebmgb.shopcart.model.CartItem;
import com.hebmgb.shopcart.model.Receipt;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    public void calculateTotals(Receipt receipt, CartItem item) {
        // Set grand total
        receipt.setGrandTotal(receipt.getGrandTotal().add(item.getPrice()));
    }
}
