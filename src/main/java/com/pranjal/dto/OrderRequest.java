package com.pranjal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String customerName;
    private String phoneNumber;
    private Double itemTotal;
    private Double tax;
    private Double grandTotal;
    private String paymentMethod;
    private List<OrderItemRequest> cartItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest{
        private String itemId;
        private String itemName;
        private String quantity;
        private String totalAmount;
    }
}
