package com.hebmgb.shopcart;

import com.hebmgb.shopcart.model.Cart;
import com.hebmgb.shopcart.service.ReceiptService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class ReceiptTests {

	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private ReceiptService receiptService;

	public static Cart mockCart;
	public static JSONObject mockItem1;
	public static JSONObject mockItem2;

	@BeforeAll
	public static void setup() throws JSONException {
		mockItem1 = new JSONObject();
		mockItem1.put("itemName", "Mock brownies");
		mockItem1.put("price", new BigDecimal(1.99));
//		mockItem1.setIsTaxable(false);
//		mockItem1.setPrice(new BigDecimal(1.99));
//		mockItem1.setSku(12345);
//		mockItem1.setOwnBrand(true);

		mockItem2 = new JSONObject();
		mockItem2.put("itemName", "Mock bananas");
		mockItem2.put("price", new BigDecimal(2.01));
//		mockItem2.setItemName("Mock bananas");
//		mockItem2.setIsTaxable(false);
//		mockItem2.setPrice(new BigDecimal(2.01));
//		mockItem2.setSku(54321);
//		mockItem2.setOwnBrand(false);

//		mockCart = new Cart();
//		mockCart.items.add(mockItem1);
//		mockCart.items.add(mockItem2);
	}
	@Test
	void calculateGrandTotal() throws Exception {
		String cart = "{\"items\":[" + mockItem1.toString() + "," + mockItem2.toString() + "]}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/receipt")
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().isOk())
				.andReturn();


		String contentResult = mvcResult.getResponse().getContentAsString();
		JSONObject receiptObject = new JSONObject(contentResult);
		assertEquals(receiptObject.get("grandTotal"), new BigDecimal(1.99 + 2.01).doubleValue());

		Mockito.verify(receiptService, atLeast(1)).calculateTotals(any(), any());
	}

}
