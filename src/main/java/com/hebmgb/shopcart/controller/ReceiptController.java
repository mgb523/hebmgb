package com.hebmgb.shopcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hebmgb.shopcart.model.Cart;
import com.hebmgb.shopcart.model.CartItem;
import com.hebmgb.shopcart.model.Receipt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReceiptController {

//    @Autowired
//    VeganizerService veganizerService;

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/receipt")
    public Receipt getReceipt(@RequestPart("cart") MultipartFile cart) throws IOException {

        Cart shoppingCart = mapper.readValue(cart.getBytes(), Cart.class);

        BigDecimal grandTotal = BigDecimal.ZERO;
        if (null != shoppingCart.items) {
            for (CartItem item : shoppingCart.items) {
                grandTotal = grandTotal.add(item.getPrice());
            }
        }

        Receipt receipt = new Receipt();
        receipt.setGrandTotal(grandTotal);
        return receipt;
    }
}
