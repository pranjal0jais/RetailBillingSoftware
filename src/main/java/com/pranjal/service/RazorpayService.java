package com.pranjal.service;

import com.pranjal.dto.OrderResponse;
import com.pranjal.dto.RazorpayResponse;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public interface RazorpayService {
    RazorpayResponse createOrder(Double amount, String currency) throws RazorpayException;

}
