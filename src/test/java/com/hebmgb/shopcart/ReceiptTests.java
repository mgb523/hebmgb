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
	public static JSONObject invalidMockCartItem1;
	public static JSONObject invalidMockCartItem2;
	public static JSONObject mockCoupon1;
	public static JSONObject invalidMockCoupon1;

	@BeforeAll
	public static void setup() throws JSONException {
		mockCartItem1 = new JSONObject();
		mockCartItem1.put("itemName", "Mock brownies");
		mockCartItem1.put("price", BigDecimal.valueOf(1.99));
		mockCartItem1.put("sku", 525659);
		mockCartItem1.put("isTaxable", true);

		mockCartItem2 = new JSONObject();
		mockCartItem2.put("itemName", "Mock bananas");
		mockCartItem2.put("price", BigDecimal.valueOf(2.01));
		mockCartItem2.put("sku", 658745);
		mockCartItem2.put("isTaxable", false);

		invalidMockCartItem1 = new JSONObject();
		invalidMockCartItem1.put("invalidField", "mock value");

		invalidMockCartItem2 = new JSONObject();
		invalidMockCartItem2.put("itemName", "Mock pizza");
		invalidMockCartItem2.put("sku", 788745);
		invalidMockCartItem2.put("price", BigDecimal.valueOf(-2.50));

		mockCoupon1 = new JSONObject();
		mockCoupon1.put("couponName", "Mock Brownie Coupon");
		mockCoupon1.put("appliedSku", 525659);
		mockCoupon1.put("discountPrice", 0.50);

		invalidMockCoupon1 = new JSONObject();
		invalidMockCoupon1.put("couponName", "Invalid Coupon");
		invalidMockCoupon1.put("appliedSku", 335659);
		invalidMockCoupon1.put("discountPrice", -0.50);
	}
	@Test
	void calculateTotalsNoCoupon() throws Exception {
		String cartNoCoupons = "{\"items\":[" + mockCartItem1.toString() + "," + mockCartItem2.toString() + "]}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cartNoCoupons.toString()))
				.andExpect(status().isOk())
				.andReturn();

		String contentResult = mvcResult.getResponse().getContentAsString();
		JSONObject receiptObject = new JSONObject(contentResult);
		assertEquals(receiptObject.get("subTotalBeforeDiscounts"), BigDecimal.valueOf(1.99 + 2.01).doubleValue());
		assertEquals(receiptObject.get("subTotalAfterDiscounts"), BigDecimal.valueOf(1.99 + 2.01).doubleValue());
		assertEquals(receiptObject.get("taxTotal"), BigDecimal.valueOf(0.16).doubleValue());
		assertEquals(receiptObject.get("taxableSubTotalAfterDiscounts"), BigDecimal.valueOf(1.99).doubleValue());
		assertEquals(receiptObject.get("discountTotal"), BigDecimal.ZERO.intValue());
		assertEquals(receiptObject.get("grandTotal"), BigDecimal.valueOf(4.16).doubleValue());

		Mockito.verify(receiptService, atMost(2)).calculateTotals(any(), any(), any());
	}

	@Test
	void calculateTotalsWithCoupons() throws Exception {
		String cartWithCoupons = "{\"items\":[" + mockCartItem1.toString() + "," + mockCartItem2.toString() + "]," +
				"\"coupons\":[" + mockCoupon1.toString() + "]}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cartWithCoupons.toString()))
				.andExpect(status().isOk())
				.andReturn();

		String contentResult = mvcResult.getResponse().getContentAsString();
		JSONObject receiptObject = new JSONObject(contentResult);
		assertEquals(receiptObject.get("subTotalBeforeDiscounts"), BigDecimal.valueOf(1.99 + 2.01).doubleValue());
		assertEquals(receiptObject.get("subTotalAfterDiscounts"), BigDecimal.valueOf(1.49 + 2.01).doubleValue());
		assertEquals(receiptObject.get("taxTotal"), BigDecimal.valueOf(0.12).doubleValue());
		assertEquals(receiptObject.get("taxableSubTotalAfterDiscounts"), BigDecimal.valueOf(1.49).doubleValue());
		assertEquals(receiptObject.get("discountTotal"), 0.50);
		assertEquals(receiptObject.get("grandTotal"), BigDecimal.valueOf(3.62).doubleValue());

		Mockito.verify(receiptService, atMost(4)).calculateTotals(any(), any(), any());
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
		assertEquals(receiptObject.get("grandTotal"), BigDecimal.ZERO.intValue());

		Mockito.verify(receiptService, atLeast(0)).calculateTotals(any(), any(), any());
	}

	@Test
	void invalidCartItemData() throws Exception {
		String cart = "{\"items\":[" + mockCartItem1.toString() + "," + invalidMockCartItem1.toString() + "]}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().is4xxClientError())
				.andReturn();

		cart = "{\"items\":[" + invalidMockCartItem2.toString() + "]}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().is4xxClientError())
				.andReturn();
	}

	@Test
	void invalidCoupons() throws Exception {
		String cart = "{\"items\":[" + mockCartItem1.toString() + "], \"coupons\":[" + invalidMockCoupon1.toString() + "]}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(cart.toString()))
				.andExpect(status().is4xxClientError())
				.andReturn();

		Mockito.verify(receiptService, atLeast(0)).calculateTotals(any(), any(), any());
	}
}
