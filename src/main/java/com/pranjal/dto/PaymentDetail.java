package com.pranjal.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private PaymentStatus status;

    public enum PaymentStatus{
        PENDING, COMPLETED, FAILED
    }
}
