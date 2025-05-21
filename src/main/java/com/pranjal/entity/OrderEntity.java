package com.pranjal.entity;

import com.pranjal.dto.PaymentDetail;
import com.pranjal.dto.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String customerName;
    private String phoneNumber;
    private Double itemTotal;
    private Double tax;
    private Double grandTotal;
    private LocalDateTime createdAt;

    @Embedded
    private PaymentDetail paymentDetail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> orderItemEntities;

    @Enumerated
    private PaymentMethod paymentMethod;

    @PrePersist
    protected void onCreate(){
        orderId = "ORD" + System.currentTimeMillis();
        createdAt = LocalDateTime.now();
    }

}
