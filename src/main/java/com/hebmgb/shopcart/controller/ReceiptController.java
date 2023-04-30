package com.hebmgb.shopcart.controller;

import com.hebmgb.shopcart.model.Cart;
import com.hebmgb.shopcart.model.CartItem;
import com.hebmgb.shopcart.model.Receipt;
import com.hebmgb.shopcart.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReceiptController {

    @Autowired
    ReceiptService receiptService;

    @PostMapping("/receipt")
    public Receipt getReceipt(@RequestBody Cart cart) {

        Receipt receipt = new Receipt();

        if (null != cart.items) {
            for (CartItem item : cart.items) {
                receiptService.calculateTotals(receipt, item);
            }
        }

        return receipt;
    }
}
