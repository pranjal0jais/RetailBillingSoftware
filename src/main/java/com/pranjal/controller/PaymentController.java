package com.pranjal.controller;

import com.pranjal.dto.OrderResponse;
import com.pranjal.dto.PaymentRequest;
import com.pranjal.dto.RazorpayResponse;
import com.pranjal.service.OrderService;
import com.pranjal.service.RazorpayService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public RazorpayResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
        return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }

    @PatchMapping("/update-payment-info")
    public OrderResponse updatePaymentInfo(@RequestBody Map<String, String> request){
        return orderService.updatePaymentInfo(
                request.get("orderId"),
                request.get("razorpayOrderId"),
                request.get("razorpayPaymentId"),
                request.get("razorpaySignature"),
                request.get("status")
        );
    }
}
