package com.pranjal.service.Impl;

import com.pranjal.dto.RazorpayResponse;
import com.pranjal.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorpayService {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;
    @Override
    public RazorpayResponse createOrder(Double amount, String currency) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(amount*100));
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());
        orderRequest.put("payment_capture",1);

        Order order = razorpayClient.orders.create(orderRequest);

        return convertToResponse(order);
    }

    private RazorpayResponse convertToResponse(Order order) {
        return RazorpayResponse.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .created_at(order.get("created_at").toString())
                .receipt(order.get("receipt"))
                .status(order.get("status"))
                .build();
    }
}
