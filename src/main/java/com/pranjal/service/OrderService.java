package com.pranjal.service;

import com.pranjal.dto.OrderRequest;
import com.pranjal.dto.OrderResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    Integer getTodayOrderCount(LocalDate date);

    Double getTodayOrderTotal(LocalDate date);

    List<OrderResponse> getRecentOrders();

    OrderResponse updatePaymentInfo(String orderId, String razorpayOrderId,
                                    String razorpayPaymentId, String razorpaySignature,
                                    String status);
}
