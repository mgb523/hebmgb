package com.hebmgb.shopcart;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class ReceiptTests {

	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private ReceiptService receiptService;

	public static String uri = "/receipt";
	public static JSONObject mockCartItem1;
	public static JSONObject mockCartItem2;
	public static JSONObject mockCartItem3;
	public static JSONObject invalidCartItem1;
	public static JSONObject invalidCartItem2;

	@BeforeAll
	public static void setup() throws JSONException {
		mockCartItem1 = new JSONObject();
		mockCartItem1.put("itemName", "Mock brownies");
		mockCartItem1.put("price", new BigDecimal(1.99));
		mockCartItem1.put("isTaxable", true);

		mockCartItem2 = new JSONObject();
		mockCartItem2.put("itemName", "Mock bananas");
		mockCartItem2.put("price", new BigDecimal(2.01));
		mockCartItem2.put("isTaxable", false);

		mockCartItem3 = new JSONObject();
		mockCartItem3.put("itemName", "Free pizza");
		mockCartItem3.put("price", new BigDecimal(0.00));

		invalidCartItem1 = new JSONObject();
		invalidCartItem1.put("invalidField", "mock value");

		invalidCartItem2 = new JSONObject();
		invalidCartItem2.put("price", new BigDecimal(-2.50));
	}
	@Test
	void calculateTotals() throws Exception {
		String cart = "{\"items\":[" + mockCartItem1.toString() + "," + mockCartItem2.toString() + "]}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().isOk())
				.andReturn();


		String contentResult = mvcResult.getResponse().getContentAsString();
		JSONObject receiptObject = new JSONObject(contentResult);
		assertEquals(receiptObject.get("subTotalBeforeDiscounts"), new BigDecimal(1.99 + 2.01).doubleValue());
		assertEquals(receiptObject.get("taxTotal"), new BigDecimal(0.16).doubleValue());
		assertEquals(receiptObject.get("taxableSubTotalAfterDiscounts"), new BigDecimal(1.99).doubleValue());
		assertEquals(receiptObject.get("grandTotal"), new BigDecimal(4.16).doubleValue());

		Mockito.verify(receiptService, atMost(2)).calculateTotals(any(), any(), any());

		cart = "{\"items\":[" + mockCartItem3.toString() + "]}";

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().isOk())
				.andReturn();


		contentResult = mvcResult.getResponse().getContentAsString();
		receiptObject = new JSONObject(contentResult);
		assertEquals(receiptObject.get("grandTotal"), 0);

		Mockito.verify(receiptService, atMost(3)).calculateTotals(any(), any(), any());
	}

	@Test
	void emptyCart() throws Exception {
		String cart = "{\"items\":[]}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().isOk())
				.andReturn();


		String contentResult = mvcResult.getResponse().getContentAsString();
		JSONObject receiptObject = new JSONObject(contentResult);
		assertEquals(receiptObject.get("grandTotal"), 0);

		Mockito.verify(receiptService, atLeast(0)).calculateTotals(any(), any(), any());
	}

	@Test
	void invalidCartData() throws Exception {
		String cart = "{\"items\":[" + mockCartItem1.toString() + "," + invalidCartItem1.toString() + "]}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().is4xxClientError())
				.andReturn();

		Mockito.verify(receiptService, atLeast(0)).calculateTotals(any(), any(), any());

		cart = "{\"items\":[" + invalidCartItem2.toString() + "]}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().is4xxClientError())
				.andReturn();

		Mockito.verify(receiptService, atLeast(0)).calculateTotals(any(), any(), any());
	}
}
