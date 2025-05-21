package com.pranjal.service.Impl;

import com.pranjal.dto.*;
import com.pranjal.entity.OrderEntity;
import com.pranjal.entity.OrderItemEntity;
import com.pranjal.repository.OrderRepository;
import com.pranjal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        OrderEntity newOrder = convertToEntity(orderRequest);

        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setStatus((Objects.equals(orderRequest.getPaymentMethod(), "CASH"))
                ? PaymentDetail.PaymentStatus.COMPLETED: PaymentDetail.PaymentStatus.PENDING);

        newOrder.setPaymentDetail(paymentDetail);
        List<OrderItemEntity> orderItemEntities = orderRequest.getCartItems()
                .stream().map(this::convertItemToEntity)
                .toList();
        newOrder.setOrderItemEntities(orderItemEntities);

        newOrder = orderRepository.save(newOrder);

        return convertToResponse(newOrder);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .orderItemResponse(newOrder.getOrderItemEntities().stream().map(this::convertToOrderItemResponse).toList())
                .itemTotal(newOrder.getItemTotal())
                .paymentDetail(newOrder.getPaymentDetail())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(newOrder.getPaymentMethod().name()))
                .createdAt(newOrder.getCreatedAt())
                .build();
    }

    private OrderResponse.OrderItemResponse convertToOrderItemResponse(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getOrderItemId())
                .itemName(orderItemEntity.getItemName())
                .quantity(orderItemEntity.getQuantity())
                .totalAmount(orderItemEntity.getTotalAmount())
                .build();
    }

    private OrderItemEntity convertItemToEntity(OrderRequest.OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .itemName(orderItemRequest.getItemName())
                .totalAmount(orderItemRequest.getTotalAmount())
                .quantity(orderItemRequest.getQuantity())
                .orderItemId(orderItemRequest.getItemId())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest orderRequest) {
        return OrderEntity.builder()
                .customerName(orderRequest.getCustomerName())
                .phoneNumber(orderRequest.getPhoneNumber())
                .itemTotal(orderRequest.getItemTotal())
                .tax(orderRequest.getTax())
                .paymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod()))
                .grandTotal(orderRequest.getGrandTotal())
                .build();
    }


    @Override
    public void deleteOrder(String orderId) {

        boolean exists = orderRepository.existsByOrderId(orderId);
        if(exists){
            OrderEntity existingOrder =
                    orderRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException(
                            "Order do not exist: " + orderId));

            orderRepository.delete(existingOrder);
        }
    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::convertToResponse).toList();
    }

    @Override
    public Integer getTodayOrderCount(LocalDate date) {
        List<OrderEntity> list =
                orderRepository.findOrderByDate(date).stream()
                        .filter((order)->order.getPaymentDetail().getStatus() == PaymentDetail.PaymentStatus.COMPLETED)
                        .toList();
        return list.size();
    }

    @Override
    public Double getTodayOrderTotal(LocalDate date) {
        List<OrderEntity> list = orderRepository.findOrderByDate(date).stream()
                .filter((order)->order.getPaymentDetail().getStatus() == PaymentDetail.PaymentStatus.COMPLETED)
                .toList();

        Double totalSales = list.stream()
                .map(OrderEntity::getGrandTotal)
                .reduce(0.0, Double::sum);

        return totalSales;
    }

    @Override
    public List<OrderResponse> getRecentOrders() {
        return orderRepository.findRecentOrders(PageRequest.of(0, 5))
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public OrderResponse updatePaymentInfo(String orderId, String razorpayOrderId,
                                           String razorpayPaymentId, String razorpaySignature,
                                           String status) {
        OrderEntity existingOrder =
                orderRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException(
                        "Order not found"));
            existingOrder.setPaymentDetail(
                    PaymentDetail.builder()
                            .razorpayPaymentId(razorpayPaymentId)
                            .razorpayOrderId(razorpayOrderId)
                            .status(PaymentDetail.PaymentStatus.valueOf(status))
                            .razorpaySignature(razorpaySignature)
                            .build()
            );
            existingOrder = orderRepository.save(existingOrder);

        return convertToResponse(existingOrder);
    }
}
