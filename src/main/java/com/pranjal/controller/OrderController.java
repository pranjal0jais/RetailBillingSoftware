package com.pranjal.controller;

import com.pranjal.dto.OrderRequest;
import com.pranjal.dto.OrderResponse;
import com.pranjal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/admin/orders/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest response){
        return orderService.createOrder(response);
    }

    @DeleteMapping("/admin/orders/delete/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/latest-orders")
    public List<OrderResponse> getLatestOrder(){
        return orderService.getLatestOrders();
    }
}
