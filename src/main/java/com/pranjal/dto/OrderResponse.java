package com.pranjal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private String customerName;
    private String phoneNumber;
    private Double itemTotal;
    private Double tax;
    private Double grandTotal;
    private PaymentMethod paymentMethod;
    private List<OrderItemResponse> orderItemResponse;
    private LocalDateTime createdAt;
    private PaymentDetail paymentDetail;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse{
        private String itemId;
        private String itemName;
        private String quantity;
        private String totalAmount;
    }
}
